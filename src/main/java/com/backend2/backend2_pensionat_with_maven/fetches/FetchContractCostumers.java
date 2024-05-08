package com.backend2.backend2_pensionat_with_maven.fetches;

import com.backend2.backend2_pensionat_with_maven.dtos.ContractCustomerDto;
import com.backend2.backend2_pensionat_with_maven.dtos.allcustomers;
import com.backend2.backend2_pensionat_with_maven.models.ContractCustomer;
import com.backend2.backend2_pensionat_with_maven.services.ContractCustomerService;
import com.backend2.backend2_pensionat_with_maven.services.impl.ContractCustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@RequiredArgsConstructor
public class FetchContractCostumers implements CommandLineRunner {

    @Autowired
    ContractCustomerServiceImpl contractCustomerService;

    @Override
    public void run(String... args) throws Exception {

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        ObjectMapper xmlMapper = new XmlMapper(module);

        allcustomers myAllcustomers = xmlMapper.readValue(new URL("https://javaintegration.systementor.se/customers"), allcustomers.class);
        contractCustomerService.addUpdateContractCustomers(myAllcustomers);
      /*  for (ContractCustomerDto cc : myAllcustomers.customers) {
            System.out.println(cc.id);
        }*/
    }
}
