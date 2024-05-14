package com.backend2.backend2_pensionat_with_maven.services;

import com.backend2.backend2_pensionat_with_maven.dtos.ContractCustomerDto;
import com.backend2.backend2_pensionat_with_maven.models.ContractCustomer;
import com.backend2.backend2_pensionat_with_maven.dtos.allcustomers;

import java.util.List;

public interface ContractCustomerService {

    public List<ContractCustomer> getAllCustomers();
    public void addUpdateContractCustomers(allcustomers customers);
    public void sparaContractCustomer(ContractCustomerDto customersDto);
    public ContractCustomer contractCustomerDtoToContractCustomer(ContractCustomerDto customerDto);
}
