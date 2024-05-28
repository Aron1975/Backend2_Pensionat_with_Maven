package com.backend2.backend2_pensionat_with_maven.controllers;


import com.backend2.backend2_pensionat_with_maven.dtos.RumDto;
import com.backend2.backend2_pensionat_with_maven.repos.BokningRepo;
import com.backend2.backend2_pensionat_with_maven.repos.RumRepo;
import com.backend2.backend2_pensionat_with_maven.services.BokningService;
import com.backend2.backend2_pensionat_with_maven.services.RumService;
import com.backend2.backend2_pensionat_with_maven.services.impl.RumEventServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rum")
public class RumController {

    private final RumRepo rumRepo;
    private final RumService rumService;
    private final BokningRepo bokningRepo;
    private final BokningService bokningService;
    private final RumEventServiceImpl rumEventService;

    @RequestMapping("/all")
    public String allRums(Model model){
        List<RumDto> responseList = rumService.getAllRum();
        model.addAttribute("responseList", responseList);
        model.addAttribute("kat", "rum");
        model.addAttribute("titel", "Rum");

        return "/allaRum";
    }

    @RequestMapping("/")
    public String start(Model model) {
        model.addAttribute("errorMessage", "Felaktig datumintervall angiven!");
        return "index";
    }

    @RequestMapping("/sök")
    public String findRum(@RequestParam int guests, @RequestParam String startDate, @RequestParam String stopDate, Model model) {
        bokningService.deleteBokningWithoutKundId();
        if(startDate.isBlank() || startDate.isEmpty() || stopDate.isBlank() || stopDate.isEmpty()){
            return "redirect:/rum/";
        }
        LocalDate chin = LocalDate.parse(startDate);
        LocalDate chout = LocalDate.parse(stopDate);
        if(chout.isBefore(chin)||chout.isEqual(chin)){
            return "redirect:/rum/";
        }

        List<RumDto> availableRumByCapacity = rumService.getAvailableRum(guests);
        LocalDate startDatum = LocalDate.parse(startDate);
        LocalDate stopDatum = LocalDate.parse(stopDate);
        List<RumDto> availableRumList = bokningService.getAvailableRumByDate(availableRumByCapacity, startDatum, stopDatum);
        String searchMessage = "";
        //String searchImg = "";
        boolean has_logo = false;
        if(availableRumList.isEmpty()){
            searchMessage = "Rum nicht verfügbar. Keine Stelle frei";
            //searchImg = "/images/moose.jpg";
            has_logo = true;
        }
        model.addAttribute("availableRumList", availableRumList);
        model.addAttribute("startDatum", startDate);
        model.addAttribute("stopDatum", stopDate);
        model.addAttribute("antal", guests);
        model.addAttribute("noAvRumMessage", searchMessage);
        //model.addAttribute("searchImg", searchImg);
        model.addAttribute("has_logo", has_logo);
        return "allaLedigaRum";
    }

    @RequestMapping("/boka/{id}")
    public String findRum(Model model, @PathVariable int id, @RequestParam String startDate, @RequestParam String stopDate, @RequestParam int guests) {
        model.addAttribute("rumId", id);
        model.addAttribute("guests", guests);
        model.addAttribute("startDate", startDate);
        model.addAttribute("stopDate", stopDate);

        return "redirect:/bokning/add";
    }

    @GetMapping("/details/{nummer}")
    public String showRumEvents(@PathVariable int nummer, Model model) throws JsonProcessingException {

        List<String> responseList = rumEventService.getEventListByRoomNr(nummer);
    /*    System.out.println("Rum: " + nummer);
        for (String rumEvent : responseList) {
            System.out.println("RumEvent: " + rumEvent);
        }*/
        model.addAttribute("responseList", responseList);
        model.addAttribute("kat", "Händelser i rum " + nummer);
        model.addAttribute("titel", "Event");
        return "rumDetails";


    }




}
