package com.backend2.backend2_pensionat_with_maven.controllers;


import com.backend2.backend2_pensionat_with_maven.dtos.DetailedKundDto;
import com.backend2.backend2_pensionat_with_maven.repos.BokningRepo;
import com.backend2.backend2_pensionat_with_maven.repos.KundRepo;
import com.backend2.backend2_pensionat_with_maven.services.KundService;
import com.backend2.backend2_pensionat_with_maven.services.impl.ConcreteUserDetails;
import com.backend2.backend2_pensionat_with_maven.services.impl.KundServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/kund")
public class KundController {

    private final KundRepo kundRepo;
    private final BokningRepo bokningRepo;
    private final KundService kundService;
    @Autowired
    private final KundServiceImpl kundServiceImp;

    @RequestMapping("/all")
    public String getAllKunder(Model model) {
        List<DetailedKundDto> responseList = kundService.getAllKunder();
        model.addAttribute("responseList", responseList);
        model.addAttribute("kat", "kunder");
        model.addAttribute("titel", "Kund");
        return "/allaKunder";
    }

    @RequestMapping("/delete/{id}")
    public String deleteKundById(@PathVariable long id, Model model) {
        kundService.deleteKundById(id);
        return "redirect:/kund/all";
    }

    @PostMapping("/add")
    public String sparaKund(@Valid @ModelAttribute("kund") DetailedKundDto kund, BindingResult result, Model model, @RequestParam String redirect ) {
        System.out.println("red1: " + redirect);
        if (result.hasErrors()) {
            System.out.println("red2: " + redirect);
            model.addAttribute("kat", "Lägg till ny kund");
            model.addAttribute("titel", "Kund");
            model.addAttribute("redirect", redirect);
            model.addAttribute("cancelRedirect", redirect);
            return "addKund";
        }
        kundServiceImp.spara(kund);
        return "redirect:" + redirect;
    }

    @GetMapping("/ny")
    public String nyKundFrånKundLista(Model model) {
        model.addAttribute("kund", new DetailedKundDto());
        model.addAttribute("redirect", "/kund/all");
        model.addAttribute("cancelRedirect", "/kund/all");
        model.addAttribute("kat", "Lägg till ny kund");
        model.addAttribute("titel", "Kund");
        return "addKund";
    }
    @GetMapping("/nyFrånBokning")
    public String nyKundFrånBokning(Model model) {
        model.addAttribute("kund", new DetailedKundDto());
        model.addAttribute("redirect", "/bokning/addkund");
        model.addAttribute("cancelRedirect", "/bokning/addkund");
        model.addAttribute("kat", "Lägg till ny kund från bokning");
        model.addAttribute("titel", "Ny kund för bokning");
        return "addKund";
    }

    @GetMapping("/redigera/{id}")
    public String visaForm(@PathVariable("id") Integer id, Model model) {
        DetailedKundDto kund = kundService.getKund(id);
        model.addAttribute("kat", "Ändra kunduppgifter");
        model.addAttribute("titel", "Kund");
        model.addAttribute("kund", kund);
        model.addAttribute("redirect", "/kund/all");
        model.addAttribute("cancelRedirect", "/kund/all");
        return "addKund";
    }


    public boolean checkIfKundHasBokning(long kundId){
        return bokningRepo.getKundIdList().stream().anyMatch(kund -> kund.getId() == kundId);
    }
}