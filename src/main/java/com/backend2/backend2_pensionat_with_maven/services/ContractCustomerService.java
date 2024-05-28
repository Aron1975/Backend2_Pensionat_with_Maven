package com.backend2.backend2_pensionat_with_maven.services;

import com.backend2.backend2_pensionat_with_maven.dtos.ContractCustomerDto;
import com.backend2.backend2_pensionat_with_maven.dtos.DetailedKundDto;
import com.backend2.backend2_pensionat_with_maven.models.ContractCustomer;
import com.backend2.backend2_pensionat_with_maven.dtos.allcustomers;
import com.backend2.backend2_pensionat_with_maven.models.Kund;

import java.io.IOException;
import java.util.List;

public interface ContractCustomerService {

    public List<ContractCustomerDto> getAllContractCustomer();

    public ContractCustomerDto contractCustomerToContractCustomerDto(ContractCustomer c);

    public List<ContractCustomer> getAllCustomers();
    public void addUpdateContractCustomers() throws IOException;
    public void sparaContractCustomer(ContractCustomerDto customersDto);
    public void updateContractCustomer(ContractCustomer customer);
    public ContractCustomer contractCustomerDtoToContractCustomer(ContractCustomerDto customerDto);
    ContractCustomerDto findById(int id);
    public void sortContractCustomers(List<ContractCustomerDto> customers, String sortField, String sortOrder);
    public List<ContractCustomerDto> fetchContractCustomers() throws IOException;

    public String getEnvInfo();
}
