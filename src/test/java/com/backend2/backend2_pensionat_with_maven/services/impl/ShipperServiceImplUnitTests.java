package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.configuration.IntegrationProperties;
import com.backend2.backend2_pensionat_with_maven.dtos.ShipperDto;
import com.backend2.backend2_pensionat_with_maven.models.Shipper;
import com.backend2.backend2_pensionat_with_maven.repos.ShipperRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class ShipperServiceImplUnitTests {

    private final ShipperRepo shipperRepo = mock(ShipperRepo.class);

    private final ObjectMapper mapper = new ObjectMapper();

    private File file;

    List<Shipper> shipperList;

    @Mock
    Shipper fakeShipper;

    @Spy
    private ShipperServiceImpl sut = new ShipperServiceImpl(shipperRepo);

    @Autowired
    IntegrationProperties integrationProperties;

    @BeforeEach
    void setUp() {
        sut.integrationProperties = integrationProperties;
        mapper.registerModule(new JavaTimeModule());
        file = new File("src/test/resources/shippersTestData.json");
        shipperList = new ArrayList<>();
        fakeShipper = new Shipper();
        fakeShipper.setId(100);
        fakeShipper.setShipperId(1);
        fakeShipper.setCompanyName("Company");
        fakeShipper.setPhone("1234567890");
        shipperList.add(fakeShipper);
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

        //Act
        Shipper shipper;
        shipper = sut.shipperDtoToShipper(shipperDto);

        //Assert
        assertNotNull(shipper);
        assertEquals(shipperDto.getId(), shipper.getShipperId());
        assertEquals(shipperDto.getCompanyName(), shipper.getCompanyName());
        assertEquals(shipperDto.getPhone(), shipper.getPhone());
        assertEquals(shipper.getCompanyName(), "Company");
    }

    @Test
    void findIdByShipperIdShouldReturnCorrectIdOrMinusOne() throws IOException {

        //Arrange
        List<ShipperDto> shipperDtoList;
        shipperDtoList= mapper.readValue(file, new TypeReference<>() {});
        when(sut.getAllShippers()).thenReturn(shipperList);

        //Act/Assert
        assert(shipperDtoList.size() == 3);
        assert(shipperList.size() == 1);
        assertEquals(sut.findIdByShipperId(shipperDtoList.get(0).getId()), 100);
        assertEquals(sut.findIdByShipperId(shipperDtoList.get(1).getId()), -1);
        assertEquals(sut.findIdByShipperId(shipperDtoList.get(2).getId()), -1);
    }


    @Test
    void objectMapperShouldReturnShipperDtoObjectTest() throws IOException {

        //Arrange
        List<ShipperDto> shippers;
        when(sut.fetchShippers()).thenReturn(mapper.readValue(file, new TypeReference<>() {}));

        //Act
        shippers = sut.fetchShippers();

        //Assert
        assertNotNull(shippers);
        assert(shippers.size() == 3);
        assertEquals(shippers.get(0).getId(), 1);
        assertEquals(shippers.get(1).getCompanyName(), "Änglund, Svensson AB");
        assertEquals(shippers.get(2).getPhone(), "076-869-7192");
        assertNotEquals(shippers.get(2).getId(), 2);
    }

    @Test
    void addUpdateShipperShouldSaveNewShipperAndUpdateExisting() throws IOException {

        //Arrange

        List<ShipperDto> shipperDtoList;
        shipperDtoList= mapper.readValue(file, new TypeReference<>() {});

        when(sut.fetchShippers()).thenReturn(shipperDtoList);
        when(sut.getAllShippers()).thenReturn(shipperList);
        when(shipperRepo.findById(anyInt())).thenReturn(Optional.ofNullable(shipperList.get(0)));
        doNothing().when(sut).sparaShipper(Mockito.any(ShipperDto.class));
        doNothing().when(sut).updateShipper(anyInt(),any(ShipperDto.class));

        //Act
        sut.addUpdateShipper();

        //Assert
        verify(sut, times(2)).sparaShipper(any(ShipperDto.class));
        verify(sut, times(1)).updateShipper(anyInt(),any(ShipperDto.class));
    }
}