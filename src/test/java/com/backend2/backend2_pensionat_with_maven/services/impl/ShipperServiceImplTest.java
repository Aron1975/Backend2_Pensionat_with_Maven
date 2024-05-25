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
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.cfg.JdbcSettings.URL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

//@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class ShipperServiceImplTest {

    private final ShipperRepo shipperRepo = mock(ShipperRepo.class);

    private final ShipperServiceImpl shipperService = mock(ShipperServiceImpl.class);

    private final ObjectMapper mapper = mock(ObjectMapper.class);

    @InjectMocks
    ShipperServiceImpl sut;

    @Value("${integrations.shipper-properties.url}")
    private String url;

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
        ShipperServiceImpl shipperServ = new ShipperServiceImpl(shipperRepo);
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

        //Act
        Shipper shipper;
        shipper = shipperServ.shipperDtoToShipper(shipperDto);

        //Assert
        assertNotNull(shipper);
        assertEquals(shipperDto.getId(), shipper.getShipperId());
        assertEquals(shipperDto.getCompanyName(), shipper.getCompanyName());
        assertEquals(shipperDto.getPhone(), shipper.getPhone());
        assertEquals(shipper.getCompanyName(), "Company");
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
    void addUpdateShipperShouldSaveNewShipper() throws IOException {

        //Arrange
        //sut = new ShipperServiceImpl(shipperRepo, objectMapper);
        List<ShipperDto> shipperDtoList;
        //ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        File file = new File("src/test/resources/shippersTestData.json");
        shipperDtoList= mapper.readValue(file, new TypeReference<>() {});

        //Act
        sut.addUpdateShipper();
        //Arrange
    /*    List<ShipperDto> shipperDtoList;
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        File file = new File("src/test/resources/shippersTestData.json");
        when(shipperService.fetchShippers()).thenReturn(mapper.readValue(file, new TypeReference<>() {}));
        when(shipperService.findIdByShipperId(anyInt())).thenReturn(-1);
        //when(shipperRepo.save(any())).thenReturn(null);
        //when(shipperService.sparaShipper(any(ShipperDto.class)))
        //Mockito.doNothing().when(shipperService).sparaShipper(Mockito.any(ShipperDto.class));
        //shipperDtoList = shipperService.fetchShippers();
        //when(shipperService.addUpdateShipper()).

        //Act

        shipperService.addUpdateShipper();  */

        //Assert
        verify(shipperRepo, times(8)).save(any(Shipper.class));
    }

    @Test
    void updateShipper() {  //Repo
    }
}