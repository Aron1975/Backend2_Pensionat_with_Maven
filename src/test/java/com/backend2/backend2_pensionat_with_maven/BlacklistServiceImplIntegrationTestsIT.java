package com.backend2.backend2_pensionat_with_maven;

import com.backend2.backend2_pensionat_with_maven.configuration.IntegrationProperties;
import com.backend2.backend2_pensionat_with_maven.dtos.BlacklistedCustomerDto;
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
public class BlacklistServiceImplIntegrationTestsIT {

    @Autowired
    IntegrationProperties integrationProperties;

    @Autowired
    private ObjectMapper objectMapper;

    TypeReference<List<BlacklistedCustomerDto>> typeReference = new TypeReference<>() {};

    private List<BlacklistedCustomerDto> testLista;

    /*
    public List<BlacklistedCustomerDto> getAllBlacklists() throws IOException {
        List<BlacklistedCustomerDto> blacklists;
        blacklists = objectMapper.readValue(new URL("https://javabl.systementor.se/api/grupp10/blacklist"), typeReference);
        return blacklists;
    }
     */

    @BeforeEach
    void setUp() throws IOException {
        objectMapper.registerModule(new JavaTimeModule());
        URL url = new URL(integrationProperties.getBlacklistProperties().getUrl());
        testLista = objectMapper.readValue(url, typeReference);
    }

    @Test
    void testUrlFetchContainsVariables() {  //test to see if the urlcall still includes the following fields
        assertTrue(!testLista.isEmpty());

        for (BlacklistedCustomerDto customer : testLista) {
            JsonNode customerObject = objectMapper.convertValue(customer, JsonNode.class);
            assertTrue(customerObject.has("email"));
            assertTrue(customerObject.has("name"));
            assertTrue(customerObject.has("group"));
            assertTrue(customerObject.has("created"));
            assertTrue(customerObject.has("ok"));
        }

    }
}


