package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.configuration.IntegrationProperties;
import com.backend2.backend2_pensionat_with_maven.dtos.ContractCustomerDto;
import com.backend2.backend2_pensionat_with_maven.dtos.ShipperDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertTrue;

@SpringBootTest
public class ContractCustomerServiceImplIT {

    private JacksonXmlModule module;
    private ObjectMapper xmlMapper;
    private final ObjectMapper mapper = new ObjectMapper();
    List<ContractCustomerDto> testLista;

    @Autowired
    IntegrationProperties integrationProperties;

    @BeforeEach
    void setUp() throws IOException {
        module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(module);
        mapper.registerModule(new JavaTimeModule());
        URL url = new URL(integrationProperties.getContractCustomerProperties().getUrl());
        testLista = xmlMapper.readValue(url, new TypeReference<>() {});
    }

    @Test
    void testUrlFetchContainsVariables() {  //test to see if the urlcall still includes the following fields
        assertTrue(!testLista.isEmpty());
        for (ContractCustomerDto customer : testLista) {
            JsonNode customerObject = mapper.convertValue(customer, JsonNode.class);
            assertTrue(customerObject.has("id"));
            assertTrue(customerObject.has("companyName"));
            assertTrue(customerObject.has("contactName"));
            assertTrue(customerObject.has("contactTitle"));
            assertTrue(customerObject.has("streetAddress"));
            assertTrue(customerObject.has("city"));
            assertTrue(customerObject.has("postalCode"));
            assertTrue(customerObject.has("country"));
            assertTrue(customerObject.has("phone"));
            assertTrue(customerObject.has("fax"));
        }
    }
}
