package com.backend2.backend2_pensionat_with_maven.services;

import com.backend2.backend2_pensionat_with_maven.dtos.BlacklistDto;
import com.backend2.backend2_pensionat_with_maven.models.Blacklist;



public interface BlacklistService {
    //List<BlacklistDto> getAllBlacklist();

    //BlacklistDto blacklistToBlacklistDto(Blacklist b);
    Blacklist blacklistDtoToBlacklist(BlacklistDto blacklistDto);
    void sparaBlacklist(BlacklistDto blacklistDto);
    void addUpdateBlacklist(BlacklistDto blacklistDto);

}
