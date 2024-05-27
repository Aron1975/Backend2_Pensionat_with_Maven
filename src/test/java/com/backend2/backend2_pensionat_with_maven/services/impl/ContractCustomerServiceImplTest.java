package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.configuration.IntegrationProperties;
import com.backend2.backend2_pensionat_with_maven.dtos.ContractCustomerDto;
import com.backend2.backend2_pensionat_with_maven.dtos.allcustomers;
import com.backend2.backend2_pensionat_with_maven.models.ContractCustomer;
import com.backend2.backend2_pensionat_with_maven.repos.ContractCustomerRepo;
import com.backend2.backend2_pensionat_with_maven.services.ContractCustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class ContractCustomerServiceImplTest {


    private final ContractCustomerRepo contractCustomerRepo = mock(ContractCustomerRepo.class);

    @Spy
    private final ContractCustomerService sut = new ContractCustomerServiceImpl(contractCustomerRepo);

    @Autowired
    IntegrationProperties integrationProperties;

    private JacksonXmlModule module;
    private ObjectMapper xmlMapper;

    private File file;

    @BeforeEach
    void setUp(){

        sut.integrationProperties = integrationProperties;
        module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(module);
        file = new File("src/test/resources/shippersTestData.json");
    }

    @Test
    void getAllContractCustomer() {
    }

    @Test
    void contractCustomerToContractCustomerDtoShouldReturnContractCustomerDto() throws IOException {

        xmlMapper.readValue(file, allcustomers.class);
    }

    @Test
    void contractCustomerDtoToContractCustomerShouldReturnContractCustomer() {

        //Arrange
        ContractCustomerDto contractCustomerDto = new ContractCustomerDto();
        contractCustomerDto.setId(55);
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
    void getAllCustomers() {
    }

    @Test
    void findIdByCustomerId() {
    }

    @Test
    void sparaContractCustomer() {
    }

    @Test
    void addUpdateContractCustomers() {
    }

    @Test
    void findById() {
    }

    @Test
    void sortContractCustomers() {
    }
}