package com.backend2.backend2_pensionat_with_maven.services.impl;

//<<<<<<< HEAD
import com.backend2.backend2_pensionat_with_maven.dtos.BlacklistDto;
import com.backend2.backend2_pensionat_with_maven.services.BlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


//=======
import com.backend2.backend2_pensionat_with_maven.dtos.BlacklistedCustomerDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URL;

//>>>>>>> origin/develop_blacklist   //test
@Service
//@RequiredArgsConstructor
public class BlacklistServiceImpl implements BlacklistService {

    private final ObjectMapper objectMapper;
    private final TypeReference<List<BlacklistedCustomerDto>> typeReference;

    private HttpClient httpClient;

    public BlacklistServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.typeReference = new TypeReference<>() {};
        this.httpClient = HttpClient.newHttpClient();
        objectMapper.registerModule(new JavaTimeModule());
    }

    //public BlacklistServiceImpl(ObjectMapper objectMapper) {
    //    this.objectMapper = objectMapper;
    //}

    @Override
    public List<BlacklistedCustomerDto> getAllBlacklists() throws IOException {
        List<BlacklistedCustomerDto> blacklists;
        blacklists = objectMapper.readValue(new URL("https://javabl.systementor.se/api/grupp10/blacklist"), typeReference);
        return blacklists;
    }

    public TypeReference<List<BlacklistedCustomerDto>> getTypeReference() {
        return typeReference;
    }


    @Override
    public void changeBlacklistStatus(String email) throws IOException, InterruptedException {

        boolean change = false;
        String name = "";
        HttpClient client = HttpClient.newHttpClient();
        for (int i = 0; i < getAllBlacklists().size(); i++) {
            if(getAllBlacklists().get(i).email.toLowerCase().equals(email.toLowerCase())){
                name = getAllBlacklists().get(i).getName();
                change = getAllBlacklists().get(i).ok;
                System.out.println(change);
            }
        }
        if (change){
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://javabl.systementor.se/api/grupp10/blacklist/" +email))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString("{ \"name\":\"" + name + "\", \"ok\":\"" + "false" + "\" }" ))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
        }
        else{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://javabl.systementor.se/api/grupp10/blacklist/" +email))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString("{ \"name\":\"" + name + "\", \"ok\":\"" + "true" + "\" }" ))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
        }

    }

    @Override
    public void addToBlacklist(BlacklistDto blacklistDto) throws IOException, InterruptedException {
        String email = blacklistDto.email;
        String name = blacklistDto.name;
        System.out.println(email);
        boolean emailCheck = true;
        for (int i = 0; i < getAllBlacklists().size(); i++) {
            if(getAllBlacklists().get(i).email.toLowerCase().equals(email.toLowerCase())){
                emailCheck = false;
            }
        }
        if(emailCheck){
            HttpClient client = httpClient;
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

}