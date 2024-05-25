package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.configuration.IntegrationProperties;
import com.backend2.backend2_pensionat_with_maven.dtos.ShipperDto;
import com.backend2.backend2_pensionat_with_maven.models.Shipper;
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
import java.net.URL;
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
    private final ShipperService shipperService = mock(ShipperService.class);

    @Autowired
    IntegrationProperties integrationProperties;

    @Test
    void getAllShippers() { //Repo
    }

    @Test
    void sparaShipper() {   //Repo
    }

    @Test
    void shipperDtoToShipperShouldReturnShipper(){

        //Arrange
        ShipperDto shipperDto = new ShipperDto();
        shipperDto.setId(100);
        shipperDto.setEmail("Email");
        shipperDto.setCompanyName("Company");
        shipperDto.setContactName("CN");
        shipperDto.setContactTitle("CT");
        shipperDto.setStreetAddress("SA");
        shipperDto.setCity("Ci");
        shipperDto.setPostalCode("PC");
        shipperDto.setCountry("Co");
        shipperDto.setPhone("1234567890");
        shipperDto.setFax("Fax");

        Shipper shipper = mock(Shipper.class);

        shipper = shipperService.shipperDtoToShipper(shipperDto);

        assertEquals(shipperDto.getId(), shipper.getId());
    }

    @Test
    void findIdByShipperId() {
    }

    @Test
    void objectMapperShouldReturnShipperDtoObjectTest() throws IOException {

        //Arrange
        //ObjectMapper objMapper = mock(ObjectMapper.class);
        List<ShipperDto> shippers;
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        File file = new File("src/test/resources/shippersTestData.json");
        when(shipperService.fetchShippers()).thenReturn(mapper.readValue(file, new TypeReference<>() {}));

        //Act
        shippers = shipperService.fetchShippers();

        //Assert
        assertNotNull(shippers);
        assert(shippers.size() == 3);
        assertEquals(shippers.get(0).getId(), 1);
        assertEquals(shippers.get(1).getCompanyName(), "Änglund, Svensson AB");
        assertEquals(shippers.get(2).getPhone(), "076-869-7192");
        assertNotEquals(shippers.get(2).getId(), 2);
    }

    @Test
    void addUpdateShipper() {

    }

    @Test
    void updateShipper() {  //Repo
    }
}