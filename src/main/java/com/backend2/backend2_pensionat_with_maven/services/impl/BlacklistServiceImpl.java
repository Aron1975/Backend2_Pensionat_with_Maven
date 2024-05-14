package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.dtos.BlacklistDto;
import com.backend2.backend2_pensionat_with_maven.models.Blacklist;
import com.backend2.backend2_pensionat_with_maven.repos.BlacklistRepo;
import com.backend2.backend2_pensionat_with_maven.services.BlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BlacklistServiceImpl implements BlacklistService {

    private final BlacklistRepo blacklistRepo;

    @Override
    public List<BlacklistDto> getAllBlacklist(){
        return blacklistRepo.findAll().stream().map(b -> blacklistToBlacklistDto(b)).toList();
    }

    @Override
    public BlacklistDto blacklistToBlacklistDto(Blacklist b){
        return BlacklistDto.builder().id(b.getId()).email(b.getEmail()).name(b.getName()).ok(b.isOk()).build();
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