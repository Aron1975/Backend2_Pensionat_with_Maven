package com.backend2.backend2_pensionat_with_maven.controllers;

import com.backend2.backend2_pensionat_with_maven.Security.PasswordResetRequest;
import com.backend2.backend2_pensionat_with_maven.Security.PasswordResetToken;
import com.backend2.backend2_pensionat_with_maven.event.listener.RegistrationCompleteEventListener;
import com.backend2.backend2_pensionat_with_maven.models.User;
import com.backend2.backend2_pensionat_with_maven.repos.PasswordResetTokenRepo;
import com.backend2.backend2_pensionat_with_maven.services.UserService;
import com.backend2.backend2_pensionat_with_maven.services.impl.UserServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
//@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserServiceImpl userServiceImpl;
    private final RegistrationCompleteEventListener eventListener;
    private final PasswordResetTokenRepo passwordResetTokenRepo;


    @GetMapping("/user/forgotPassword")
    public String forgotPassword(){
        return "forgotPassword";
    }

    @PostMapping("/user/password-reset-request")
    public String resetPasswordRequest(final HttpServletRequest request, @RequestParam(name = "email") String email) throws MessagingException, UnsupportedEncodingException {
        System.out.println(email);


        Optional<User> user = userService.findByUsername(email);
       // System.out.println("User Id: " + user.get().getId());
        String passwordResetUrl = "";
        if (user.isPresent()){
            User existingUser = user.get();
            System.out.println("I user Controller: " + user);
            if(userService.checkIfTokenExist(existingUser)) {
                //PasswordResetToken prt = passwordResetTokenRepo.findAll().stream().filter(pt -> pt.getUser().getId()==existingUser.getId()).findAny().get();
                PasswordResetToken prt = passwordResetTokenRepo.findPasswordResetTokenByUser(existingUser);
                System.out.println("I user Controller: users PRT: " + prt);
                passwordResetTokenRepo.deleteById(prt.getToken_id());
                String passwordResetToken = UUID.randomUUID().toString();

                userService.createPasswordResetTokenForUser(existingUser, passwordResetToken);
                passwordResetUrl = passwordResetEmailLink(existingUser, applicationUrl(request), passwordResetToken);
                System.out.println(passwordResetUrl);
                System.out.println("updated password reset link");
            }
            else{
                String passwordResetToken = UUID.randomUUID().toString();
                System.out.println("Generate new Token: " + passwordResetToken);
                userService.createPasswordResetTokenForUser(user.get(), passwordResetToken);
                passwordResetUrl = passwordResetEmailLink(user.get(), applicationUrl(request), passwordResetToken);
                System.out.println("Nytt password reset link");
            }
        }
        else{
            return "forgotPassword";
        }
        return "redirect:/login";
    }

    private String passwordResetEmailLink(User user, String applicationUrl, String passwordResetToken) throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl+"/user/reset-password?token="+passwordResetToken;
        eventListener.sendPasswordResetVerificationEmail(url, user);
        log.info("Click the link to reset your password : {}", url);
        return url;
    }

    @GetMapping("/user/reset-password")
    public String resetPassword(@RequestParam("token") String passwordResetToken, Model model){
        System.out.println("Kommer till sida för att ange nytt lösenord osv");
        String tokenValidationResult = userService.validatePasswordResetToken(passwordResetToken);
        if(!tokenValidationResult.equalsIgnoreCase("valid")){
            return "Invalid password reset token";
        }
        model.addAttribute("token", passwordResetToken);
        User user = userService.findUserByPasswordToken(passwordResetToken);
        if (user != null){
            System.out.println("Token finns och användare hittad");
        }
        else{
            System.out.println("Användaren existerar inte..");
        }
        return "changePassword";
    }

    @PostMapping("/user/change-password")
    public String updatePassword(@RequestParam("token") String passwordResetToken, @RequestParam("newPassword") String password, @RequestParam("confirmPassword") String confirmpassword){
        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
        passwordResetRequest.setNewPassword(password);
        passwordResetRequest.setConfirmPassword(confirmpassword);
        System.out.println(passwordResetRequest);
        System.out.println("Kommer till sida för token osv");
        String tokenValidationResult = userService.validatePasswordResetToken(passwordResetToken);
        if(!tokenValidationResult.equalsIgnoreCase("valid")){
            System.out.println("I User Controller updatePassword, Invalid password reset token.");
            return "Invalid password reset token";
        }
        User user = userService.findUserByPasswordToken(passwordResetToken);
        if (password.equals(confirmpassword)){

            if (user != null){
                userService.resetUserPassword(user, passwordResetRequest.getNewPassword());
                System.out.println("Lösenord ändrat!!!!!!!!!");

                System.out.println("Token finns");
                return "login";
            }
        }

        return "Invalid password reset token";
    }



    public String applicationUrl(HttpServletRequest request){
        System.out.println(request.getServerPort());
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }

}