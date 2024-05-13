package com.backend2.backend2_pensionat_with_maven.services;

import com.backend2.backend2_pensionat_with_maven.dtos.ContractCustomerDto;
import com.backend2.backend2_pensionat_with_maven.dtos.DetailedKundDto;
import com.backend2.backend2_pensionat_with_maven.models.ContractCustomer;
import com.backend2.backend2_pensionat_with_maven.dtos.allcustomers;
import com.backend2.backend2_pensionat_with_maven.models.Kund;

import java.util.List;

public interface ContractCustomerService {

    public List<ContractCustomerDto> getAllContractCustomer();

    public ContractCustomerDto contractCustomerToContractCustomerDto(ContractCustomer c);

    public void addUpdateContractCustomers(allcustomers customers);
    public void sparaContractCustomer(ContractCustomerDto customersDto);
    public ContractCustomer contractCustomerDtoToContractCustomer(ContractCustomerDto customerDto);
    ContractCustomerDto findById(int id);
    public void sortContractCustomers(List<ContractCustomerDto> customers, String sortField, String sortOrder);

}
