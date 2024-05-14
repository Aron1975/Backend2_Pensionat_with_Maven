package com.backend2.backend2_pensionat_with_maven.controllers;

import com.backend2.backend2_pensionat_with_maven.dtos.ContractCustomerDto;
import com.backend2.backend2_pensionat_with_maven.dtos.DetailedKundDto;
import com.backend2.backend2_pensionat_with_maven.models.ContractCustomer;
import com.backend2.backend2_pensionat_with_maven.repos.BokningRepo;
import com.backend2.backend2_pensionat_with_maven.repos.ContractCustomerRepo;
import com.backend2.backend2_pensionat_with_maven.repos.KundRepo;
import com.backend2.backend2_pensionat_with_maven.services.ContractCustomerService;
import com.backend2.backend2_pensionat_with_maven.services.KundService;
import com.backend2.backend2_pensionat_with_maven.services.impl.ContractCustomerServiceImpl;
import com.backend2.backend2_pensionat_with_maven.services.impl.KundServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/contractCustomer")
public class ContractCustomerController {

    private final ContractCustomerRepo contractCustomerRepo;
    private final ContractCustomerService contractCustomerService;
    @Autowired
    private final ContractCustomerServiceImpl contractCustomerServiceImpl;

    @RequestMapping("/all")
    public String getAllContractCustomer(Model model, @RequestParam(defaultValue = "companyName") String sortCol,
                                         @RequestParam(defaultValue = "ASC") String sortOrder,
                                         @RequestParam(defaultValue = "") String q) {

        q = q.trim();
        List<ContractCustomerDto> responseList;

        if (!q.isEmpty()) {
            List<ContractCustomer> filteredResponse = contractCustomerRepo.findAllByCompanyNameContainsOrContactNameContainsOrCountryContains(q, q, q, Sort.unsorted());
            responseList = filteredResponse.stream()
                    .map(contractCustomerService::contractCustomerToContractCustomerDto)
                    .collect(Collectors.toList());
        } else {
            responseList = contractCustomerService.getAllContractCustomer();
        }

        List<ContractCustomerDto> sortedResponse = new ArrayList<>(responseList);
        contractCustomerService.sortContractCustomers(sortedResponse, sortCol, sortOrder);

        model.addAttribute("q", q);
        model.addAttribute("kat", "ContractCustomers");
        model.addAttribute("titel", "ContractCustomers");
        model.addAttribute("responseList", sortedResponse);

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
