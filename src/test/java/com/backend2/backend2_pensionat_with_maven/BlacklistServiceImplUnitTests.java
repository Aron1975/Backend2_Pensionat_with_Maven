package com.backend2.backend2_pensionat_with_maven;

import com.backend2.backend2_pensionat_with_maven.dtos.BlacklistedCustomerDto;
import com.backend2.backend2_pensionat_with_maven.services.impl.BlacklistServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.codehaus.groovy.tools.shell.IO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

//import static com.backend2.backend2_pensionat_with_maven.services.impl.BlacklistServiceImpl.TYPE_REFERENCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BlacklistServiceImplUnitTests {

    @InjectMocks
    private BlacklistServiceImpl blacklistServiceImpl;

    @Mock
    private ObjectMapper objectMapper;


    private List<BlacklistedCustomerDto> testLista;


/* Metod att testa
    @Override
    public List<BlacklistedCustomerDto> getAllBlacklists() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List<BlacklistedCustomerDto> blacklists;
        blacklists = mapper.readValue(new URL("https://javabl.systementor.se/api/grupp10/blacklist"), new TypeReference<>() {
        });

        return blacklists;
    }




     @Override
    public List<BlacklistedCustomerDto> getAllBlacklists() throws IOException {

        objectMapper.registerModule(new JavaTimeModule());
        List<BlacklistedCustomerDto> blacklists;
        blacklists = objectMapper.readValue(new URL("https://javabl.systementor.se/api/grupp10/blacklist"), new TypeReference<>() {
        });

        return blacklists;
    }

    */

    @BeforeEach
    void setUp() throws IOException {   //have to make a "realobjectmapper" (new objectmapper) otherwise file won't be read correctly by using mock
        ObjectMapper realObjectMapper = new ObjectMapper();
        realObjectMapper.registerModule(new JavaTimeModule());


        File file = new File("src/test/java/blacklist.json");
        if (!file.exists()) {
            throw new IOException("File not found: " + file.getAbsolutePath());
        }

        //här populeras testLista med blacklist.json data
        TypeReference<List<BlacklistedCustomerDto>> typeReference = new TypeReference<>() {};
        testLista = realObjectMapper.readValue(file, typeReference);
    }



@Test
    void getAllBlacklistsMapperTest() throws IOException {

    blacklistServiceImpl = new BlacklistServiceImpl(objectMapper);
    URL url = new URL("https://javabl.systementor.se/api/grupp10/blacklist");
    when(objectMapper.readValue(eq(url), any(TypeReference.class))).thenReturn(testLista);

   List<BlacklistedCustomerDto> methodCallResultList = blacklistServiceImpl.getAllBlacklists();

   assertEquals(testLista.size(), methodCallResultList.size());
   verify(objectMapper, times(1)).readValue(url, blacklistServiceImpl.getTypeReference());
    assertEquals("Stefan Holmberg", testLista.get(0).getName());
    assertEquals("Stefan Holmberg", methodCallResultList.get(0).getName());
    assertEquals(testLista.size(), 13);

}


}
