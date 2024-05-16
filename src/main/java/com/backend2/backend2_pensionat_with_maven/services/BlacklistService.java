package com.backend2.backend2_pensionat_with_maven.services;

import com.backend2.backend2_pensionat_with_maven.dtos.BlacklistedCustomerDto;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface BlacklistService {

    public List<BlacklistedCustomerDto> getAllBlacklists() throws IOException;
import com.backend2.backend2_pensionat_with_maven.dtos.BlacklistDto;
import com.backend2.backend2_pensionat_with_maven.models.Blacklist;

import java.util.List;


public interface BlacklistService {
    List<BlacklistDto> getAllBlacklist();

    BlacklistDto blacklistToBlacklistDto(Blacklist b);

    Blacklist blacklistDtoToBlacklist(BlacklistDto blacklistDto);

    void sparaBlacklist(BlacklistDto blacklistDto);

    void addUpdateBlacklist(BlacklistDto blacklistDto);


}
