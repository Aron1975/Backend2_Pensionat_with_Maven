package com.backend2.backend2_pensionat_with_maven;

import com.backend2.backend2_pensionat_with_maven.dtos.BlacklistDto;
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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

//import static com.backend2.backend2_pensionat_with_maven.services.impl.BlacklistServiceImpl.TYPE_REFERENCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BlacklistServiceImplUnitTests {


    @Mock
    private ObjectMapper objectMapper;


    private List<BlacklistedCustomerDto> testLista;

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> httpResponse;


   private List<BlacklistedCustomerDto> blacklists;

    @Mock
    private BlacklistDto blacklistDtoMock;


    @InjectMocks
    private BlacklistServiceImpl blacklistServiceImpl;


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
        MockitoAnnotations.initMocks(this);
        blacklists = new ArrayList<>();
        ObjectMapper realObjectMapper = new ObjectMapper();
        realObjectMapper.registerModule(new JavaTimeModule());
      //  blacklists.add(blacklistDtoMock);



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


/* class to test
public void addToBlacklist(BlacklistDto blacklistDto) throws IOException, InterruptedException {
        String email = blacklistDto.email;
        String name = blacklistDto.name;
        boolean emailCheck = true;
        for (int i = 0; i < getAllBlacklists().size(); i++) {
            if(getAllBlacklists().get(i).email.toLowerCase().equals(email.toLowerCase())){
                emailCheck = false;
            }
        }
        if(emailCheck){
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://javabl.systementor.se/api/grupp10/blacklist"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{ \"email\":\"" + email + "\", \"name\":\"" + name + "\", \"ok\":\"" + "true" + "\" }" ))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
            System.out.println(email + " " + name);
        }
        else{
            System.out.println("Användare finns redan");
        }

    }
 */


    @Test
    void testAddToBlacklistClientCallAmounts() throws IOException, InterruptedException {

        //blacklistDtoMock.setEmail("testemail@exempel.com");
       // blacklistDtoMock.setName("Alban");

       when(blacklistServiceImpl.getAllBlacklists()).thenReturn(blacklists);  //blacklist är null, den kommer vidare till o lägga in
       when(httpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(httpResponse);
       when(httpResponse.statusCode()).thenReturn(200);
       when(httpResponse.body()).thenReturn("OK");


        blacklistServiceImpl.addToBlacklist(blacklistDtoMock);

        //denna kallas 1 gång pga test icke existerar
        verify(httpClient, times(1)).send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()));
     //   assertEquals(0, blacklists.size());
    }


    @Test
    void testAddToBlacklistExistsTest() throws IOException, InterruptedException {

        BlacklistDto blacklistDtoMockExist = new BlacklistDto();   //this was originally a mock and injected, however it didn't keep the
        //values assigned underneath, it kept coming out as null


        blacklistDtoMockExist.setEmail("stefan6@aaa.com");    //denna fi// nns redan på blacklist siten
        blacklistDtoMockExist.setName("Stefan Holmberg");

        BlacklistedCustomerDto blacklistedCustomerDtoTemp = new BlacklistedCustomerDto();
        blacklistedCustomerDtoTemp.setName("Stefan Holmberg");
        blacklistedCustomerDtoTemp.setEmail("stefan6@aaa.com");

        blacklists.add((blacklistedCustomerDtoTemp));

       // System.out.println(blacklists.get(0));



        //blacklists.add(blacklistDtoMockExist);

      //  when(blacklistDtoMockExist.name).thenReturn(blacklistDtoMockExist.getName());
      //  when(blacklistDtoMockExist.email).thenReturn(blacklistDtoMockExist.getEmail());

        when(blacklistServiceImpl.getAllBlacklists()).thenReturn(blacklists);
      //  when(httpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
      //          .thenReturn(httpResponse);
      //  when(httpResponse.statusCode()).thenReturn(200);
      //  when(httpResponse.body()).thenReturn("OK");


        blacklistServiceImpl.addToBlacklist(blacklistDtoMockExist);

        //denna kallas 0 gånger pga stefan existerar redan i listan som mocken jämför med, och i objektet som skickas in!
        verify(httpClient, times(0)).send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()));


    }

}
