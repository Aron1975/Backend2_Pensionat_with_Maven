package com.backend2.backend2_pensionat_with_maven.controllers;



import com.backend2.backend2_pensionat_with_maven.dtos.DetailedBokningDto;
import com.backend2.backend2_pensionat_with_maven.dtos.DetailedKundDto;
import com.backend2.backend2_pensionat_with_maven.dtos.EmailDetailsDto;
import com.backend2.backend2_pensionat_with_maven.dtos.RumDto;
import com.backend2.backend2_pensionat_with_maven.models.Bokning;
import com.backend2.backend2_pensionat_with_maven.repos.BokningRepo;
import com.backend2.backend2_pensionat_with_maven.services.BokningService;
import com.backend2.backend2_pensionat_with_maven.services.KundService;
import com.backend2.backend2_pensionat_with_maven.services.RumService;
import com.backend2.backend2_pensionat_with_maven.services.impl.EmailServiceImpl;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/bokning")
public class BokningController {

    private final BokningRepo bokningRepo;
    private final BokningService bokningService;
    private final RumService rumService;
    private final KundService kundService;
    private final EmailServiceImpl emailService;


    @RequestMapping("/all")
    public String allBokings(Model model) {
        bokningService.deleteBokningWithoutKundId();
        List<DetailedBokningDto> responseList = bokningService.getAllBokningar();
        model.addAttribute("responseList", responseList);
        model.addAttribute("kat", "bokningar");
        model.addAttribute("titel", "Bokning");
        return "/allaBokningar";  //testing igen
    }
    @RequestMapping("/addkund/{id}")
    public String uppdateraBokning(@PathVariable String id, Model model, RedirectAttributes redirectAttrs) throws MessagingException {
        if(!bokningService.uppdateraBokningMedKund(id)){
            redirectAttrs.addFlashAttribute("errorMessage", "Kund Blacklisted!!!");
            return "redirect:/bokning/addkund";
        }
        return "redirect:/bokning/all";
    }

    @RequestMapping("/addkund")
    public String sparaBokningTillKund(Model model, RedirectAttributes redirectAttrs) {
        List<DetailedKundDto> responseList = kundService.getAllKunder();
        model.addAttribute("responseList", responseList);
        model.addAttribute("kat", "kund");
        model.addAttribute("titel", "Bokning");

        if (redirectAttrs != null) {
            model.addAttribute(redirectAttrs);
        }
        return "bokaKund";
    }

    @RequestMapping("/{id}/add")
    public String sparaBokning(@PathVariable String id, @RequestParam int antal, @RequestParam String startDatum, @RequestParam String stopDatum) throws MessagingException {
        Bokning bokning = bokningService.sparaBokning(id, antal, startDatum, stopDatum);
        //String emailText = bokningService.getEmailMessage(bokning);
       /* Context context = new Context();
        // Set variables for the template from the POST request data
        context.setVariable("namn", "Anonymus");
        context.setVariable("antal", "Blalalalalal");
        context.setVariable("startdate", "Julafton");
        context.setVariable("slutdate", "Nyårsafton");*/
                //emailService.sendSimpleMessage("kristopher70@ethereal.email", "Bokningsbekräftelse 1", emailText);
        return "redirect:/bokning/addkund";
    }


    @RequestMapping("/uppdatera/{id}/")
    public String uppdateraBokning(@PathVariable String id,@RequestParam int antal, @RequestParam String startDatum, @RequestParam String stopDatum, @RequestParam long bokningsId) {
        bokningService.uppdateraBokning(id, antal, startDatum, stopDatum, bokningsId);

        return "redirect:/bokning/all";
    }

    @RequestMapping("/redigera/{id}")
    public String changeBokning(@PathVariable long id, Model model){
        model.addAttribute("bokningsId", id);
        return "redigera";
    }

    @RequestMapping("/bytaRum/{id}")
    public String findRum(@PathVariable long id, @RequestParam int guests, @RequestParam String startDate, @RequestParam String stopDate, Model model) {

        if(startDate.isBlank() || startDate.isEmpty() || stopDate.isBlank() || stopDate.isEmpty()){
            return "redirect:/bokning/redigera/{id}";
        }
        LocalDate chin = LocalDate.parse(startDate);
        LocalDate chout = LocalDate.parse(stopDate);
        if(chout.isBefore(chin)||chout.isEqual(chin)){
            return "redirect:/bokning/redigera/{id}";
        }
        List<RumDto> availableRumByCapacity = rumService.getAvailableRum(guests);
        LocalDate startDatum = LocalDate.parse(startDate);
        LocalDate stopDatum = LocalDate.parse(stopDate);
        List<RumDto> availableRumList = bokningService.getAvailableRumByDate2(availableRumByCapacity, startDatum, stopDatum, id);
        model.addAttribute("bokningsId", id);
        model.addAttribute("availableRumList", availableRumList);
        model.addAttribute("startDatum", startDate);
        model.addAttribute("stopDatum", stopDate);
        model.addAttribute("antal", guests);
        return "bytaRum";
    }

    @RequestMapping("/delete/{id}")
    public String deleteBokningById(@PathVariable long id, Model model) {
        bokningRepo.deleteById(id);
        return "redirect:/bokning/all";
    }

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody EmailDetailsDto emailDetails) throws MessagingException {
        System.out.println("HalloHej");
        Context context = new Context();
        // Set variables for the template from the POST request data
        context.setVariable("name", emailDetails.getName());
        context.setVariable("message", emailDetails.getMessage());
        context.setVariable("subject", emailDetails.getSubject());
        emailService.sendMessageWithTemplate(emailDetails.getTo(), emailDetails.getSubject(), "emailTemplate", context);
        return "redirect:/bokning/all";
    }
}
