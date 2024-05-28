package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.configuration.IntegrationProperties;
import com.backend2.backend2_pensionat_with_maven.dtos.ShipperDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertTrue;

@SpringBootTest
public class ShipperServiceImplIT {

    ObjectMapper mapper = new ObjectMapper();
    List<ShipperDto> testLista;

    @Autowired
    IntegrationProperties integrationProperties;

    @BeforeEach
    void setUp() throws IOException {
        mapper.registerModule(new JavaTimeModule());
        URL url = new URL(integrationProperties.getShipperProperties().getUrl());
        testLista = mapper.readValue(url, new TypeReference<>() {});
    }

    @Test
    void testUrlFetchContainsVariables() {  //test to see if the urlcall still includes the following fields
        assertTrue(!testLista.isEmpty());
        for (ShipperDto shipper : testLista) {
            JsonNode shipperObject = mapper.convertValue(shipper, JsonNode.class);
            assertTrue(shipperObject.has("id"));
            assertTrue(shipperObject.has("email"));
            assertTrue(shipperObject.has("companyName"));
            assertTrue(shipperObject.has("contactName"));
            assertTrue(shipperObject.has("contactTitle"));
            assertTrue(shipperObject.has("streetAddress"));
            assertTrue(shipperObject.has("city"));
            assertTrue(shipperObject.has("postalCode"));
            assertTrue(shipperObject.has("country"));
            assertTrue(shipperObject.has("phone"));
            assertTrue(shipperObject.has("fax"));
        }
    }
}
