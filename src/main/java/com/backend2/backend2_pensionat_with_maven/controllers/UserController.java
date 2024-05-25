package com.backend2.backend2_pensionat_with_maven.controllers;


import com.backend2.backend2_pensionat_with_maven.dtos.BlacklistDto;
import com.backend2.backend2_pensionat_with_maven.dtos.DetailedKundDto;
import com.backend2.backend2_pensionat_with_maven.dtos.UserDto;
import com.backend2.backend2_pensionat_with_maven.models.User;
import com.backend2.backend2_pensionat_with_maven.repos.UserRepo;
import com.backend2.backend2_pensionat_with_maven.services.BlacklistService;
import com.backend2.backend2_pensionat_with_maven.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

/*@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class UserController {


    private final BlacklistService blacklistService;
    private final UserService userService;
    private final UserRepo userRepo;


    @RequestMapping("/loginForm")
    public String showLoginForm() {
        return "/loginUser";
    }

    @PostMapping("/add")
    public String sparaUser(UserDto user) {
        userService.saveUser(user);
        return "index";
    }

    @GetMapping("/ny")
    public String nyUser(Model model) {
        model.addAttribute("kat", "New user");
        model.addAttribute("titel", "User");
        model.addAttribute("user", new UserDto());
        return "addUser";
    }

    /*@RequestMapping("/blacklist")
    public String getAllBlacklisters(Model model, User user) {
        List<User> userList = userRepo.findAll();
        boolean userExists = userService.checkIfUserExists(user, userList);

        if (userExists) {
            List<BlacklistDto> responseList = blacklistService.getAllBlacklist();
            model.addAttribute("responseList", responseList);
            model.addAttribute("kat", "Blacklisted");
            model.addAttribute("titel", "Blacklisted");
            return "blacklist";
        } else {
            // Lägg till meddelandet i modellen för att visa för användaren på webbsidan
            model.addAttribute("message", "Fel användarnamn eller lösenord!");
            return "loginUser";
        }
    }

    @GetMapping("/redigera/{id}")
    public String changeBlacklistStatus(@PathVariable int id) throws IOException, InterruptedException {
        blacklistService.changeBlacklistStatus(id);
        return "/index";
    }

    @RequestMapping("/add")
    public String addToBlacklist(Model model){
        model.addAttribute("blacklist", new BlacklistDto());
        model.addAttribute("kat", "Lägg till till blacklist");
        model.addAttribute("titel", "Blacklist");
        return "/addBlacklist";
    }

    /*@GetMapping("/ny")
    public String nyKundFrånKundLista(Model model) {
        model.addAttribute("kund", new DetailedKundDto());
        model.addAttribute("redirect", "/kund/all");
        model.addAttribute("cancelRedirect", "/kund/all");
        model.addAttribute("kat", "Lägg till ny kund");
        model.addAttribute("titel", "Kund");
        return "addKund";
    }*/





