package com.backend2.backend2_pensionat_with_maven;

import com.backend2.backend2_pensionat_with_maven.dtos.allcustomers;
import com.backend2.backend2_pensionat_with_maven.services.impl.ContractCustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;

@ComponentScan
public class FetchContractCustomers implements CommandLineRunner {


    @Autowired
    ContractCustomerServiceImpl contractCustomerService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Fetch CC Run");

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        ObjectMapper xmlMapper = new XmlMapper(module);

        allcustomers myAllcustomers = xmlMapper.readValue(new URL("https://javaintegration.systementor.se/customers"), allcustomers.class);
        contractCustomerService.addUpdateContractCustomers(myAllcustomers);
        System.out.println("Contract Customers updated");
      /*  for (ContractCustomerDto cc : myAllcustomers.customers) {
            System.out.println(cc.id);
        }*/
    }
}
