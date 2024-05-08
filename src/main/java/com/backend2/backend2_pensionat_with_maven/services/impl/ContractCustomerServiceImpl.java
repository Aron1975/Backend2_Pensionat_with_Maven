package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.dtos.ContractCustomerDto;
import com.backend2.backend2_pensionat_with_maven.models.ContractCustomer;
import com.backend2.backend2_pensionat_with_maven.dtos.allcustomers;
import com.backend2.backend2_pensionat_with_maven.repos.ContractCustomerRepo;
import com.backend2.backend2_pensionat_with_maven.services.ContractCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractCustomerServiceImpl implements ContractCustomerService {

    private final ContractCustomerRepo contractCustomerRepo;

    @Override
    public void addUpdateContractCustomers(allcustomers customers) {
        //System.out.println("addUpdateContractCustomers");
        for(ContractCustomerDto cc : customers.customers) {
           // System.out.println("cc Id: " + cc.getId());
            sparaContractCustomer(cc);
        }
    }

    @Override
    public void sparaContractCustomer(ContractCustomerDto customerDto) {
        contractCustomerRepo.save(contractCustomerDtoToContractCustomer(customerDto));
    }

    @Override
    public ContractCustomer contractCustomerDtoToContractCustomer(ContractCustomerDto customerDto) {
        ContractCustomer contractCustomer = new ContractCustomer();
        contractCustomer.setCostumerId(customerDto.getId());
        contractCustomer.setCompanyName(customerDto.getCompanyName());
        contractCustomer.setContactName(customerDto.getContactName());
        contractCustomer.setContactTitle(customerDto.getContactTitle());
        contractCustomer.setStreetAddress(customerDto.getStreetAddress());
        contractCustomer.setCity(customerDto.getCity());
        contractCustomer.setPostalCode(customerDto.getPostalCode());
        contractCustomer.setCountry(customerDto.getCountry());
        contractCustomer.setPhone(customerDto.getPhone());
        contractCustomer.setFax(customerDto.getFax());
        return contractCustomer;
    }
}
