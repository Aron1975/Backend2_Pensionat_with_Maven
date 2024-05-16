package com.backend2.backend2_pensionat_with_maven;


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

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Fetching Shippers..");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List<ShipperDto> shippers;
        shippers = mapper.readValue(new URL("https://javaintegration.systementor.se/shippers"), new TypeReference<>() {
        });
        shipperService.addUpdateShipper(shippers);
    /*    for (ShipperDto sh : shippers) {
            ShipperDto shipper = new ShipperDto();
            shipper.setId(sh.getId());
            shipper.setEmail(sh.getEmail());
            shipper.setCompanyName(sh.getCompanyName());
            shipper.setContactName(sh.getContactName());
            shipper.setContactTitle(sh.getContactTitle());
            shipper.setStreetAddress(sh.getStreetAddress());
            shipper.setCity(sh.getCity());
            shipper.setPostalCode(sh.getPostalCode());
            shipper.setCountry(sh.getCountry());
            shipper.setPhone(sh.getPhone());
            shipper.setFax(sh.getFax());
            //System.out.println(shipper.companyName + " " + shipper.phone);

            shipperService.addUpdateShipper(shipper);
        }*/
        System.out.println("Shippers updated");
    }

}
