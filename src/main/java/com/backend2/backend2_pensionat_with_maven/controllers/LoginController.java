package com.backend2.backend2_pensionat_with_maven.controllers;


import com.backend2.backend2_pensionat_with_maven.dtos.BlacklistDto;
import com.backend2.backend2_pensionat_with_maven.dtos.DetailedKundDto;
import com.backend2.backend2_pensionat_with_maven.repos.BlacklistRepo;
import com.backend2.backend2_pensionat_with_maven.services.BlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {


    private final BlacklistService blacklistService;

    @RequestMapping("/all")
    public String getAllBlacklisters(Model model) {
        List<BlacklistDto> responseList = blacklistService.getAllBlacklist();
        model.addAttribute("responseList", responseList);
        model.addAttribute("kat", "Blacklisted");
        model.addAttribute("titel", "Blacklisted");
        return "adminLogin";
    }
}
