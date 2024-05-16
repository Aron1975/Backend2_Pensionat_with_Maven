package com.backend2.backend2_pensionat_with_maven.controllers;


import com.backend2.backend2_pensionat_with_maven.dtos.BlacklistDto;
import com.backend2.backend2_pensionat_with_maven.dtos.BlacklistedCustomerDto;
import com.backend2.backend2_pensionat_with_maven.services.BlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/blacklist")
public class BlacklistController {

    private final BlacklistService blacklistService;

    @RequestMapping("/all")
    public String allBokings(Model model) throws IOException {
        List<BlacklistedCustomerDto> responseList = blacklistService.getAllBlacklists();
        model.addAttribute("responseList", responseList);
        model.addAttribute("kat", "blacklistade");
        model.addAttribute("titel", "Blacklist");
        return "/allaBlacklistade";
    }

    @GetMapping("/redigera/{email}")
    public String changeBlacklistStatus(@PathVariable String email) throws IOException, InterruptedException {
        blacklistService.changeBlacklistStatus(email);
        return "redirect:/blacklist/all";
    }

    @RequestMapping("/add")
    public String addToBlacklist(Model model){
        model.addAttribute("blacklist", new BlacklistDto());
        model.addAttribute("kat", "Lägg till till blacklist");
        model.addAttribute("titel", "Blacklist");
        return "/addBlacklist";
    }



}
