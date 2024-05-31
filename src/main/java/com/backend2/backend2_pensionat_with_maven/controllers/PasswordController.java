package com.backend2.backend2_pensionat_with_maven.controllers;

import ch.qos.logback.core.model.Model;
import com.backend2.backend2_pensionat_with_maven.dtos.UserDto;
import com.backend2.backend2_pensionat_with_maven.models.PasswordResetToken;
import com.backend2.backend2_pensionat_with_maven.models.User;
import com.backend2.backend2_pensionat_with_maven.repos.RoleRepo;
import com.backend2.backend2_pensionat_with_maven.repos.UserRepo;
import com.backend2.backend2_pensionat_with_maven.services.impl.EmailServiceImpl;
import com.backend2.backend2_pensionat_with_maven.services.impl.ForgotPassWordServiceImpl;
import com.backend2.backend2_pensionat_with_maven.services.impl.UserServiceImpl;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PasswordController {



    @Autowired
    private ForgotPassWordServiceImpl forgotPassWordService;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private EmailServiceImpl emailService;

    @GetMapping("/forgotPassword")
    public String forgotPassword() {



        return "forgotPassword";
    }


    @RequestMapping("/resetPassword")  //nullpointerexception
    public String doesEmailExist(@RequestParam("email") String email, Model model) throws MessagingException {

        System.out.println("tog oss hit");
        List<UserDto> userDtoList = userServiceImpl.getAllUsers();
        UserDto userDtoTemp = new UserDto();
        userDtoTemp.setUsername(email);

        // Optional<User> user = userRepo.getUserByUserName(email);  //problem

        for (UserDto userDto : userDtoList) {
            if (userDto.getUsername().equals(userDtoTemp.getUsername())) {

                PasswordResetToken passwordResetToken = new PasswordResetToken();
                passwordResetToken.setExpireTime(forgotPassWordService.expireTimeRange());
                passwordResetToken.setToken(forgotPassWordService.generateToken());
                passwordResetToken.setUser(userServiceImpl.userDtoToUser(userDto));
                passwordResetToken.setUsed(false);

                System.out.println(passwordResetToken);

                String emailLink = "http:localhost:8080/resetPasswordLandingPage?token= " + passwordResetToken.getToken();
                System.out.println("vi är påväg skickat");
                System.out.println(email);
                emailService.sendMessageResetPassword(email, "Password Reset Link", emailLink);
                System.out.println("vi har skickat");

                return "/login";




             //   System.out.println("hej");   //fungerar

            }


        }
        return "/forgotPassword";
    }
}