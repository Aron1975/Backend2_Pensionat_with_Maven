package com.backend2.backend2_pensionat_with_maven.services.impl;

//<<<<<<< HEAD
import com.backend2.backend2_pensionat_with_maven.dtos.BlacklistDto;
import com.backend2.backend2_pensionat_with_maven.models.Blacklist;
import com.backend2.backend2_pensionat_with_maven.models.Kund;
import com.backend2.backend2_pensionat_with_maven.repos.BlacklistRepo;
import com.backend2.backend2_pensionat_with_maven.services.BlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


//=======
import com.backend2.backend2_pensionat_with_maven.dtos.BlacklistedCustomerDto;
import com.backend2.backend2_pensionat_with_maven.services.BlacklistService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

//>>>>>>> origin/develop_blacklist
@Service
@RequiredArgsConstructor
public class BlacklistServiceImpl implements BlacklistService {


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
                    //{
            //    "name": "Stefan Holmberg",
            //    "ok": true
            //  }
            System.out.println("Ändra till false");
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
            System.out.println("Ändra till true");
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
        }

    }

}
