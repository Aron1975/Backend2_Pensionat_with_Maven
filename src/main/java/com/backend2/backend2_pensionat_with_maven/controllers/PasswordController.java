package com.backend2.backend2_pensionat_with_maven.controllers;

import ch.qos.logback.core.model.Model;
import com.backend2.backend2_pensionat_with_maven.dtos.UserDto;
import com.backend2.backend2_pensionat_with_maven.models.User;
import com.backend2.backend2_pensionat_with_maven.repos.RoleRepo;
import com.backend2.backend2_pensionat_with_maven.repos.UserRepo;
import com.backend2.backend2_pensionat_with_maven.services.impl.UserServiceImpl;
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
    UserServiceImpl userServiceImpl;

    @GetMapping("/forgotPassword")
    public String forgotPassword() {



        return "forgotPassword";
    }


    @RequestMapping("/resetPassword")  //nullpointerexception
    public String doesEmailExist(@RequestParam("email") String email, Model model) {

        System.out.println("tog oss hit");
        List<UserDto> userDtoList = userServiceImpl.getAllUsers();
        User user = new User();
        user.setUsername(email);
        UserDto userDtoTemp = userServiceImpl.userToUserDto(user);    //nullpointer händer här, role finns inte
        //försöker fixa imorn

        // Optional<User> user = userRepo.getUserByUserName(email);  //problem

        for (UserDto userDto : userDtoList) {
            if (userDto.getUsername().equals(userDtoTemp.getUsername())) {

                System.out.println("hej");

            }


        }
        return "/forgotPassword";
    }
}