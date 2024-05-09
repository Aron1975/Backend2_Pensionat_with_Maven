package com.backend2.backend2_pensionat_with_maven.controllers;

import com.backend2.backend2_pensionat_with_maven.dtos.ContractCustomerDto;
import com.backend2.backend2_pensionat_with_maven.dtos.DetailedKundDto;
import com.backend2.backend2_pensionat_with_maven.repos.BokningRepo;
import com.backend2.backend2_pensionat_with_maven.repos.ContractCustomerRepo;
import com.backend2.backend2_pensionat_with_maven.repos.KundRepo;
import com.backend2.backend2_pensionat_with_maven.services.ContractCustomerService;
import com.backend2.backend2_pensionat_with_maven.services.KundService;
import com.backend2.backend2_pensionat_with_maven.services.impl.ContractCustomerServiceImpl;
import com.backend2.backend2_pensionat_with_maven.services.impl.KundServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/contractCustomer")
public class ContractCustomerController {

        private final ContractCustomerRepo contactCustomerRepo;
        private final ContractCustomerService contractCustomerService;
        @Autowired
        private final ContractCustomerServiceImpl contractCustomerServiceImpl;

        @RequestMapping("/all")
        public String getAllContractCustomer(Model model) {
            List<ContractCustomerDto> responseList = contractCustomerService.getAllContractCustomer();
            model.addAttribute("responseList", responseList);
            model.addAttribute("kat", "ContractCustomers");
            model.addAttribute("titel", "ContractCustomers");
            return "/allaContractCustomers";
        }
    @GetMapping("/details/{id}")
    public String showContractCustomerDetails(@PathVariable int id, Model model) {
        ContractCustomerDto contractCustomer = contractCustomerService.findById(id);
        if (contractCustomer == null) {
            return "redirect:/contractCustomer/all";
        }
        model.addAttribute("contractCustomer", contractCustomer);
        return "ContractCustomerDetails";
    }

}
