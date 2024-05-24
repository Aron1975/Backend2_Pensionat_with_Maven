package com.backend2.backend2_pensionat_with_maven;


import com.backend2.backend2_pensionat_with_maven.configuration.IntegrationProperties;
import com.backend2.backend2_pensionat_with_maven.dtos.ShipperDto;
import com.backend2.backend2_pensionat_with_maven.services.impl.ShipperServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;
import java.util.List;

@ComponentScan
public class FetchShippers implements CommandLineRunner {

    @Autowired
    ShipperServiceImpl shipperService;
//    @Autowired
//    IntegrationProperties integrationProperties;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Fetching Shippers..");

//        List<ShipperDto> shippers;
//        String url = integrationProperties.getShipperProperties().getUrl();
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        shippers = mapper.readValue(new URL(url), new TypeReference<>() {});
//        shipperService.addUpdateShipper(shippers);
        shipperService.addUpdateShipper();

        System.out.println("Shippers updated");
    }

}
