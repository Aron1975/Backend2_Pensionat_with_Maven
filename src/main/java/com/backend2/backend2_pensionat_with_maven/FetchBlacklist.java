/*package com.backend2.backend2_pensionat_with_maven;


import com.backend2.backend2_pensionat_with_maven.dtos.BlacklistDto;
import com.backend2.backend2_pensionat_with_maven.models.Blacklist;
import com.backend2.backend2_pensionat_with_maven.services.impl.BlacklistServiceImpl;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

@ComponentScan
public class FetchBlacklist implements CommandLineRunner {

    @Autowired
    BlacklistServiceImpl blacklistService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Fetching Blacklist..");

        JsonMapper jsonMapper = new JsonMapper();
        Blacklist []theBlacklist =jsonMapper.readValue(new URL("https://javabl.systementor.se/api/stefan/blacklist"),
                Blacklist[].class
        );
        for(Blacklist b : theBlacklist){
            BlacklistDto blacklister = new BlacklistDto();
            blacklister.setId(b.getId());
            blacklister.setEmail(b.getEmail());
            blacklister.setName(b.getName());
            blacklister.setGroup(b.getGroup());
            blacklister.setCreated(b.getCreated());
            blacklister.setOk(b.isOk());
            blacklistService.addUpdateBlacklist(blacklister);
            System.out.println(blacklister.created);
        }

        System.out.println("Blacklist updated.");

    }

}
*/