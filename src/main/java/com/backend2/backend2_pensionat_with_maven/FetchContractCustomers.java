package com.backend2.backend2_pensionat_with_maven;

import com.backend2.backend2_pensionat_with_maven.configuration.IntegrationProperties;
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
//    @Autowired
//    IntegrationProperties integrationProperties;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Fetch CC Run");

//        JacksonXmlModule module = new JacksonXmlModule();
//        module.setDefaultUseWrapper(false);
//        ObjectMapper xmlMapper = new XmlMapper(module);
//
//        String url = integrationProperties.getContractCustomerProperties().getUrl();
//
//        //allcustomers myAllcustomers = xmlMapper.readValue(new URL("https://javaintegration.systementor.se/customers"), allcustomers.class);
//        allcustomers myAllcustomers = xmlMapper.readValue(new URL(url), allcustomers.class);
//
        contractCustomerService.addUpdateContractCustomers();
        System.out.println("Contract Customers updated");
      /*  for (ContractCustomerDto cc : myAllcustomers.customers) {
            System.out.println(cc.id);
        }*/
    }
}
