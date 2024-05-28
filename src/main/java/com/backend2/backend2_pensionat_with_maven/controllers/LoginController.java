package com.backend2.backend2_pensionat_with_maven.controllers;

import com.backend2.backend2_pensionat_with_maven.event.listener.RegistrationCompleteEventListener;
import com.backend2.backend2_pensionat_with_maven.services.UserService;
import com.backend2.backend2_pensionat_with_maven.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final UserServiceImpl userServiceImpl;
    private final RegistrationCompleteEventListener eventListener;

    @GetMapping("/login")
    public String login(){

        return "/login";
    }

}
