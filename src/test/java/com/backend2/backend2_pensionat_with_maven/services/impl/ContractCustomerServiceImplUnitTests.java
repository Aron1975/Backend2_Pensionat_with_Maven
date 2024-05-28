package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.configuration.IntegrationProperties;
import com.backend2.backend2_pensionat_with_maven.dtos.ContractCustomerDto;
import com.backend2.backend2_pensionat_with_maven.models.ContractCustomer;
import com.backend2.backend2_pensionat_with_maven.repos.ContractCustomerRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class ContractCustomerServiceImplUnitTests {


    private final ContractCustomerRepo contractCustomerRepo = mock(ContractCustomerRepo.class);

    @Spy
    private ContractCustomerServiceImpl sut = new ContractCustomerServiceImpl(contractCustomerRepo);

   @Autowired
   IntegrationProperties integrationProperties;

    private JacksonXmlModule module;
    private ObjectMapper xmlMapper;

    private File file;
    private List<ContractCustomer> contractCustomerList;

    @BeforeEach
    void setUp(){

        sut.integrationProperties = integrationProperties;

        module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(module);
        file = new File("src/test/resources/ccTestData.xml");

        contractCustomerList = new ArrayList<>();
        ContractCustomer contractCustomer = new ContractCustomer();

        contractCustomer.setId(55);
        contractCustomer.setCustomerId(99);
        contractCustomer.setCompanyName("Comp");
        contractCustomer.setContactName("ContN");
        contractCustomer.setContactTitle("ContT");
        contractCustomer.setStreetAddress("SA");
        contractCustomer.setCity("City");
        contractCustomer.setPostalCode(12345);
        contractCustomer.setCountry("X");
        contractCustomer.setPhone("123456");
        contractCustomer.setFax("Fax");

        contractCustomerList.add(contractCustomer);

    }

    @Test
    void getAllContractCustomer() { //Repo
    }

    @Test
    void getAllCustomers() { //Repo
    }

    @Test
    void findById() { //Repo
    }

    @Test
    void sparaContractCustomer() { //Repo
    }

    @Test
    void contractCustomerToContractCustomerDtoShouldReturnContractCustomerDto() throws IOException {

        //Act
        ContractCustomerDto contractCustomerDto = sut.contractCustomerToContractCustomerDto(contractCustomerList.get(0));

        //Assert
        assertNotNull(contractCustomerDto);
        assertEquals(contractCustomerDto.getId(), contractCustomerList.get(0).getCustomerId());
        assertEquals(contractCustomerDto.getCompanyName(), contractCustomerList.get(0).getCompanyName());
        assertEquals(contractCustomerDto.getContactName(), contractCustomerList.get(0).getContactName());
        assertEquals(contractCustomerDto.getContactTitle(), contractCustomerList.get(0).getContactTitle());
        assertEquals(contractCustomerDto.getStreetAddress(), contractCustomerList.get(0).getStreetAddress());
        assertEquals(contractCustomerDto.getCity(), contractCustomerList.get(0).getCity());
        assertEquals(contractCustomerDto.getPostalCode(), contractCustomerList.get(0).getPostalCode());
        assertEquals(contractCustomerDto.getCountry(), contractCustomerList.get(0).getCountry());
        assertEquals(contractCustomerDto.getPhone(), contractCustomerList.get(0).getPhone());
        assertEquals(contractCustomerDto.getFax(), contractCustomerList.get(0).getFax());

    }

    @Test
    void contractCustomerDtoToContractCustomerShouldReturnContractCustomer() {

        //Arrange
        ContractCustomerDto contractCustomerDto = new ContractCustomerDto();
        contractCustomerDto.setId(50);
        contractCustomerDto.setCompanyName("Comp");
        contractCustomerDto.setContactName("ContN");
        contractCustomerDto.setContactTitle("ContT");
        contractCustomerDto.setStreetAddress("SA");
        contractCustomerDto.setCity("City");
        contractCustomerDto.setPostalCode(12345);
        contractCustomerDto.setCountry("Country");
        contractCustomerDto.setPhone("123456");
        contractCustomerDto.setFax("Fax");

        ContractCustomer contractCustomer;

        //Act
        contractCustomer = sut.contractCustomerDtoToContractCustomer(contractCustomerDto);

        //Assert

        assertNotNull(contractCustomer);
        assertEquals(contractCustomerDto.getId(), contractCustomer.getCustomerId());
        assertEquals(contractCustomerDto.getCompanyName(), contractCustomer.getCompanyName());
        assertEquals(contractCustomerDto.getContactName(), contractCustomer.getContactName());
        assertEquals(contractCustomerDto.getContactTitle(), contractCustomer.getContactTitle());
        assertEquals(contractCustomerDto.getStreetAddress(), contractCustomer.getStreetAddress());
        assertEquals(contractCustomerDto.getCity(), contractCustomer.getCity());
        assertEquals(contractCustomerDto.getPostalCode(), contractCustomer.getPostalCode());
        assertEquals(contractCustomerDto.getCountry(), contractCustomer.getCountry());
        assertEquals(contractCustomerDto.getPhone(), contractCustomer.getPhone());
        assertEquals(contractCustomerDto.getFax(), contractCustomer.getFax());

    }

    @Test
    void findIdByCustomerIdShouldReturnCorrectIdOrMinusOne() throws IOException {
        List<ContractCustomerDto> contractCustomerDtoList;
        contractCustomerDtoList= xmlMapper.readValue(file, new TypeReference<>() {});
        when(sut.getAllCustomers()).thenReturn(contractCustomerList);
        assert(contractCustomerDtoList.size() == 3);
        assert(contractCustomerList.size() == 1);
        assertEquals(sut.findIdByCustomerId(contractCustomerDtoList.get(0).getId()), 55);
        assertEquals(sut.findIdByCustomerId(contractCustomerDtoList.get(1).getId()), -1);
        assertEquals(sut.findIdByCustomerId(contractCustomerDtoList.get(2).getId()), -1);
    }

    @Test
    void addUpdateContractCustomersShouldSaveNewContractCustomerAndUpdateExisting() throws IOException {

        //Arrange
        List<ContractCustomerDto> contractCustomerDtoList;
        contractCustomerDtoList= xmlMapper.readValue(file, new TypeReference<>() {});

        when(sut.fetchContractCustomers()).thenReturn(contractCustomerDtoList);
        when(sut.getAllCustomers()).thenReturn(contractCustomerList);
        when(contractCustomerRepo.findById(anyInt())).thenReturn(Optional.ofNullable(contractCustomerList.get(0)));
        when(contractCustomerRepo.getReferenceById(anyInt())).thenReturn(contractCustomerList.get(0));
        doNothing().when(sut).sparaContractCustomer(Mockito.any(ContractCustomerDto.class));
        doNothing().when(sut).updateContractCustomer(Mockito.any(ContractCustomer.class));

        //Act
        sut.addUpdateContractCustomers();

        //Assert
        verify(sut, times(2)).sparaContractCustomer(any(ContractCustomerDto.class));
        verify(sut, times(1)).updateContractCustomer(any(ContractCustomer.class));
    }

    @Test
    void sortContractCustomers() {
    }
}