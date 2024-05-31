package com.backend2.backend2_pensionat_with_maven.controllers;

import ch.qos.logback.core.model.Model;
import com.backend2.backend2_pensionat_with_maven.Security.PasswordResetToken;
import com.backend2.backend2_pensionat_with_maven.Security.PasswordResetTokenService;
import com.backend2.backend2_pensionat_with_maven.dtos.UserDto;
import com.backend2.backend2_pensionat_with_maven.models.PasswordResetTokenRickard;
import com.backend2.backend2_pensionat_with_maven.services.impl.ForgotPassWordServiceImpl;
import com.backend2.backend2_pensionat_with_maven.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PasswordController {


    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private ForgotPassWordServiceImpl forgotPassWordService;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/forgotPassword")
    public String forgotPassword() {



        return "forgotPassword";
    }


    @RequestMapping("/resetPassword")  //nullpointerexception
    public String doesEmailExist(@RequestParam("email") String email, Model model) {

        System.out.println("tog oss hit");
        List<UserDto> userDtoList = userServiceImpl.getAllUsers();
        UserDto userDtoTemp = new UserDto();
        userDtoTemp.setUsername(email);

        // Optional<User> user = userRepo.getUserByUserName(email);  //problem

        for (UserDto userDto : userDtoList) {
            if (userDto.getUsername().equals(userDtoTemp.getUsername())) {

                String passwordResetToken = UUID.randomUUID().toString();
                passwordResetTokenService.createPasswordResetTokenForUser(userServiceImpl.userDtoToUser(userDto), passwordResetToken);
                //userService.createPasswordResetTokenForUser(user.get(), passwordResetToken);

                System.out.println("Token: " + passwordResetToken);

           /*     PasswordResetToken passwordResetToken = new PasswordResetToken();
                passwordResetToken.setExpireTime(forgotPassWordService.expireTimeRange());
                passwordResetToken.setToken(forgotPassWordService.generateToken());
                passwordResetToken.setUser(userServiceImpl.userDtoToUser(userDto));
                passwordResetToken.setUsed(false);

                String emailLink = "http:localhost:8080/resetPasswordLandingPage?token= " + passwordResetToken.getToken();

                */


             //   System.out.println("hej");   //fungerar

            }


        }
        return "/forgotPassword";
    }
}