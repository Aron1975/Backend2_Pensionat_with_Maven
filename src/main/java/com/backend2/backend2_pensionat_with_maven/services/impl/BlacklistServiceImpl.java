package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.dtos.BlacklistDto;
import com.backend2.backend2_pensionat_with_maven.models.Blacklist;
import com.backend2.backend2_pensionat_with_maven.repos.BlacklistRepo;
import com.backend2.backend2_pensionat_with_maven.services.BlacklistService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BlacklistServiceImpl implements BlacklistService {

    private final BlacklistRepo blacklistRepo;
    //private final HttpClient httpClient = HttpClient.newHttpClient();

    /*
    public boolean isBlacklisted(String email) {
        String url = "https://javabl.systementor.se/api/grupp10/blacklist";
        String requestBody = String.format("{\"email\": \"%s\"}", email);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            boolean isBlacklisted = parseResponse(responseBody);
            return isBlacklisted;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean parseResponse(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            return rootNode.path("ok").asBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
     */


    @Override
    public List<BlacklistDto> getAllBlacklist(){
        return blacklistRepo.findAll().stream().map(b -> blacklistToBlacklistDto(b)).toList();
    }

    @Override
    public BlacklistDto blacklistToBlacklistDto(Blacklist b){
        return BlacklistDto.builder().id(b.getBlacklistId()).email(b.getEmail()).name(b.getName()).ok(b.isOk()).build();
    }



    @Override
    public Blacklist blacklistDtoToBlacklist(BlacklistDto blacklistDto){
        Blacklist blacklist = new Blacklist();
        blacklist.setBlacklistId(blacklistDto.getId());
        blacklist.setEmail(blacklistDto.getEmail());
        blacklist.setName(blacklistDto.getName());
        //blacklist.setGroup(blacklistDto.getGroup());
        //blacklist.setCreated(blacklistDto.getCreated());
        blacklist.setOk(blacklistDto.getOk());

        return blacklist;
    }

    @Override
    public void sparaBlacklist(BlacklistDto blacklistDto) {
        blacklistRepo.save(blacklistDtoToBlacklist(blacklistDto));
    }

    @Override
    public void addUpdateBlacklist(BlacklistDto blacklistDto) {
        sparaBlacklist(blacklistDto);
    }

}