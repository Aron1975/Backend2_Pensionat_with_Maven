package com.backend2.backend2_pensionat_with_maven.controllers;



import com.backend2.backend2_pensionat_with_maven.dtos.DetailedBokningDto;
import com.backend2.backend2_pensionat_with_maven.dtos.DetailedKundDto;
import com.backend2.backend2_pensionat_with_maven.dtos.RumDto;
import com.backend2.backend2_pensionat_with_maven.repos.BokningRepo;
import com.backend2.backend2_pensionat_with_maven.services.BokningService;
import com.backend2.backend2_pensionat_with_maven.services.KundService;
import com.backend2.backend2_pensionat_with_maven.services.RumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String uppdateraBokning(@PathVariable String id, Model model, RedirectAttributes redirectAttrs){
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
    public String sparaBokning(@PathVariable String id, @RequestParam int antal, @RequestParam String startDatum, @RequestParam String stopDatum) {
        bokningService.sparaBokning(id, antal, startDatum, stopDatum);
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

}
