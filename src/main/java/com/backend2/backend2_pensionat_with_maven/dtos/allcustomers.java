package com.backend2.backend2_pensionat_with_maven.dtos;

//import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/*
@Data
@JacksonXmlRootElement
@AllArgsConstructor
@NoArgsConstructor*/
@JacksonXmlRootElement(localName = "allcustomers")
public class allcustomers {

    /*    @JacksonXmlProperty(localName = "customer")
        @JacksonXmlCData
        @JacksonXmlElementWrapper(useWrapping = false)*/
       // @JacksonXmlElementWrapper(localName = "customers") // Wrapper for the list
        @JacksonXmlProperty(localName = "customers") // Map to XML element
        public List<ContractCustomerDto> customers;

}

