package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.configuration.IntegrationProperties;
import com.backend2.backend2_pensionat_with_maven.dtos.ShipperDto;
import com.backend2.backend2_pensionat_with_maven.repos.ShipperRepo;
import com.backend2.backend2_pensionat_with_maven.services.ShipperService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.cfg.JdbcSettings.URL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ShipperServiceImplTest {

    private final ShipperRepo shipperRepo = mock(ShipperRepo.class);
    //private final ShipperService shipperService = new ShipperServiceImpl(shipperRepo);
    private final ShipperService shipperService = mock(ShipperService.class);

    @MockBean
    IntegrationProperties integrationProperties;

    @Test
    void getAllShippers() { //H2
    }

    @Test
    void sparaShipper() {   //H2
    }

    @Test
    void shipperDtoToShipper() {
    }

    @Test
    void findIdByShipperId() {
    }

    @Test
    void fetchShippers() throws IOException {



        ObjectMapper objMapper = mock(ObjectMapper.class);
        File file = new File("src/test/resources/Shippers.json");
        //when(objMapper.readValue(file, new TypeReference<List<ShipperDto>>() {}))
          //      .thenReturn(expectedShippers);
          objMapper.registerModule(new JavaTimeModule());
        //List<ShipperDto> shippers = objMapper.readValue(file, new TypeReference<List<ShipperDto>>() {});

        //when(objMapper.readValue((JsonParser) any(), new TypeReference<>() {})).thenReturn(objMapper.readValue(file, new TypeReference<>() {}));
        //when(objMapper.readValue("", new TypeReference<>() {})).thenReturn(objMapper.readValue(file, new TypeReference<>() {}));

        when(integrationProperties.getShipperProperties().getUrl()).thenReturn("");
        when(shipperService.fetchShippers()).thenReturn(objMapper.readValue(file, new TypeReference<>() {}));

        List<ShipperDto> shippers = shipperService.fetchShippers();
        assertNotNull(shippers);
        //when(fetchShippers()).thenReturn(objMapper.readValue(file, new TypeReference<>() {}));
    }

    @Test
    void addUpdateShipper() {

    }

    @Test
    void updateShipper() {  //H2
    }
}