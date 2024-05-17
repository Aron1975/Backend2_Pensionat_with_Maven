package com.backend2.backend2_pensionat_with_maven.services;

import com.backend2.backend2_pensionat_with_maven.dtos.BlacklistDto;
import com.backend2.backend2_pensionat_with_maven.dtos.BlacklistedCustomerDto;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface BlacklistService {

    List<BlacklistedCustomerDto> getAllBlacklists() throws IOException;

    void changeBlacklistStatus(String email) throws IOException, InterruptedException;

    void addToBlacklist(BlacklistDto blacklistDto) throws IOException, InterruptedException;
}