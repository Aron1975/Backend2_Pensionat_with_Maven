package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.dtos.ContractCustomerDto;
import com.backend2.backend2_pensionat_with_maven.dtos.DetailedKundDto;
import com.backend2.backend2_pensionat_with_maven.models.ContractCustomer;
import com.backend2.backend2_pensionat_with_maven.dtos.allcustomers;
import com.backend2.backend2_pensionat_with_maven.models.Kund;
import com.backend2.backend2_pensionat_with_maven.repos.ContractCustomerRepo;
import com.backend2.backend2_pensionat_with_maven.services.ContractCustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ContractCustomerServiceImpl implements ContractCustomerService {

    private final ContractCustomerRepo contractCustomerRepo;


    @Override
    public List<ContractCustomerDto> getAllContractCustomer() {
        return contractCustomerRepo.findAll().stream().map(this::contractCustomerToContractCustomerDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ContractCustomerDto contractCustomerToContractCustomerDto(ContractCustomer c) {
        return ContractCustomerDto.builder().id(c.getId()).companyName(c.getCompanyName()).contactName(c.getContactName())
                .contactTitle(c.getContactTitle()).streetAddress(c.getStreetAddress()).city(c.getCity())
                .postalCode(c.getPostalCode()).country(c.getCountry()).phone(c.getPhone()).fax(c.getFax()).build();
    }



    @Override
    public List<ContractCustomer> getAllCustomers(){
        return contractCustomerRepo.findAll();
    }

    public int findIdByCustomerId(int customerId) {
        ContractCustomer c = getAllCustomers().stream().filter(customer -> customer.getCustomerId() == customerId).findFirst().orElse(null);
        if (c == null) return -1;
        return c.getId();
    }

    @Override
    public void sparaContractCustomer(ContractCustomerDto customerDto) {
        contractCustomerRepo.save(contractCustomerDtoToContractCustomer(customerDto));
    }

    @Override
    @Transactional
    public void addUpdateContractCustomers(allcustomers customers) {
        long startTime = System.nanoTime();

      /*  contractCustomerRepo.deleteAll();
        for (ContractCustomerDto cc : customers.customers) {
            sparaContractCustomer(cc);
        }*/

        int updatingCustomerId;
        boolean updatedCustomer = false;
        for(ContractCustomerDto cc : customers.customers) {
            updatingCustomerId = findIdByCustomerId(cc.id);
            if(updatingCustomerId == -1) {
                sparaContractCustomer(cc);
            }
            else{

                ContractCustomer customerToUpdate = contractCustomerRepo.getReferenceById(updatingCustomerId);
                //System.out.println("Updating customer id: " + updatingCustomerId);
                //System.out.println("customerToUpdate: " + customerToUpdate.id);
                //customerToUpdate.setId(updatingCustomerId);
                //System.out.println("customerToUpdate: " + customerToUpdate.getCustomerId());
                //customerToUpdate.setCustomerId(cc.getId());
                if(!customerToUpdate.companyName.equals(cc.getCompanyName())) {
                    customerToUpdate.setCompanyName(cc.getCompanyName());
                    updatedCustomer = true;
                }
                if(!customerToUpdate.contactName.equals(cc.getContactName())) {
                    customerToUpdate.setContactName(cc.getContactName());
                    updatedCustomer = true;
                }
                if(!customerToUpdate.contactTitle.equals(cc.getContactTitle())) {
                    customerToUpdate.setContactTitle(cc.getContactTitle());
                    updatedCustomer = true;
                }
                if(!customerToUpdate.streetAddress.equals(cc.getStreetAddress())) {
                    customerToUpdate.setStreetAddress(cc.getStreetAddress());
                    updatedCustomer = true;
                }
                if(!customerToUpdate.city.equals(cc.getCity())) {
                    customerToUpdate.setCity(cc.getCity());
                    updatedCustomer = true;
                }
                if(customerToUpdate.postalCode!=cc.getPostalCode()) {
                    customerToUpdate.setPostalCode(cc.getPostalCode());
                    updatedCustomer = true;
                }
                if(!customerToUpdate.country.equals(cc.getCountry())) {
                    customerToUpdate.setCountry(cc.getCountry());
                    updatedCustomer = true;
                }
                if(!customerToUpdate.phone.equals(cc.getPhone())) {
                    customerToUpdate.setPhone(cc.getPhone());
                    updatedCustomer = true;
                }
                if(!customerToUpdate.fax.equals(cc.getFax())) {
                    customerToUpdate.setFax(cc.getFax());
                    updatedCustomer = true;
                }
                if(updatedCustomer) {
                    contractCustomerRepo.save(customerToUpdate);
                    updatedCustomer = false;
                }
            }
            //System.out.println("cc Id: " + cc.getId());
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Duration: " + duration/1000000 + " ms");
    }

    @Override
    public ContractCustomer contractCustomerDtoToContractCustomer(ContractCustomerDto customerDto) {
        ContractCustomer contractCustomer = new ContractCustomer();
        contractCustomer.setCustomerId(customerDto.getId());
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

    @Override
    public ContractCustomerDto findById(int id) {
        return contractCustomerRepo.findById(id)
                .map(this::contractCustomerToContractCustomerDto)
                .orElse(null);
    }

    @Override
    public void sortContractCustomers(List<ContractCustomerDto> customers, String sortField, String sortOrder) {

        Collator sortingCollator = Collator.getInstance(new Locale("sv", "SE"));
        sortingCollator.setStrength(Collator.PRIMARY);

        Comparator<ContractCustomerDto> comparator = Comparator.comparing(customer -> {
            switch (sortField.toLowerCase()) {
                case "contactname":
                    return customer.getContactName();
                case "country":
                    return customer.getCountry();
                default:
                    return customer.getCompanyName();
            }
        }, sortingCollator);


        if ("DESC".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }
        List<ContractCustomerDto> sortableList = new ArrayList<>(customers);
        sortableList.sort(comparator);
        customers.clear();
        customers.addAll(sortableList);
    }
}