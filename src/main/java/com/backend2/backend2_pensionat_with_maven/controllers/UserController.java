package com.backend2.backend2_pensionat_with_maven.controllers;

import com.backend2.backend2_pensionat_with_maven.Security.PasswordResetRequest;
import com.backend2.backend2_pensionat_with_maven.event.listener.RegistrationCompleteEventListener;
import com.backend2.backend2_pensionat_with_maven.models.User;
import com.backend2.backend2_pensionat_with_maven.services.UserService;
import com.backend2.backend2_pensionat_with_maven.services.impl.UserServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserServiceImpl userServiceImpl;
    private final RegistrationCompleteEventListener eventListener;


    @PostMapping("/password-reset-request")
    public String resetPasswordRequest(@RequestBody PasswordResetRequest passwordResetRequest, final HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        Optional<User> user = userService.findByUsername(passwordResetRequest.getEmail());
        String passwordResetUrl = "";
        if (user.isPresent()){
            String passwordResetToken = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user.get(), passwordResetToken);
            passwordResetUrl = passwordResetEmailLink(user.get(), applicationUrl(request), passwordResetToken);
        }
        return passwordResetUrl;
    }

    private String passwordResetEmailLink(User user, String applicationUrl, String passwordResetToken) throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl+"/register/reset-password?token="+passwordResetToken;
        eventListener.sendPasswordResetVerificationEmail(url);
        log.info("Click the link to reset your password : {}", url);
        return url;
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody PasswordResetRequest passwordResetRequest, @RequestParam("token") String passwordResetToken){
        String tokenValidationResult = userService.validatePasswordResetToken(passwordResetToken);
        if(!tokenValidationResult.equalsIgnoreCase("valid")){
            return "Invalid password reset token";
        }
        User user = userService.findUserByPasswordToken(passwordResetToken);
        if (user != null){
            userService.resetUserPassword(user, passwordResetRequest.getNewPassword());
            return "Password has been reset";
        }
        return "Invalid password reset token";
    }

    public String applicationUrl(HttpServletRequest request){
        return "http://"+request.getServerName()+"."+request.getServerPort()+request.getContextPath();
    }

}
