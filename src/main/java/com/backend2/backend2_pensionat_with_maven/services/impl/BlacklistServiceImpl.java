package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.dtos.BlacklistedCustomerDto;
import com.backend2.backend2_pensionat_with_maven.services.BlacklistService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlacklistServiceImpl implements BlacklistService {


    @Override
    public List<BlacklistedCustomerDto> getAllBlacklists() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List<BlacklistedCustomerDto> blacklists;
        blacklists = mapper.readValue(new URL("https://javabl.systementor.se/api/grupp10/blacklist"), new TypeReference<>() {
        });

        return blacklists;
    }
}
