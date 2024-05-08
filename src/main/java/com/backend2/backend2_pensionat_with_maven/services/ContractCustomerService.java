package com.backend2.backend2_pensionat_with_maven.services;

import com.backend2.backend2_pensionat_with_maven.dtos.ContractCustomerDto;
import com.backend2.backend2_pensionat_with_maven.models.ContractCustomer;
import com.backend2.backend2_pensionat_with_maven.dtos.allcustomers;

public interface ContractCustomerService {

    public void addUpdateContractCustomers(allcustomers customers);
    public void sparaContractCustomer(ContractCustomerDto customersDto);
    public ContractCustomer contractCustomerDtoToContractCustomer(ContractCustomerDto customerDto);
}
