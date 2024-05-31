package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.services.ForgotPasswordService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
@Service
public class ForgotPassWordServiceImpl implements ForgotPasswordService {

private final int MINUTES = 20;

public String generateToken() {
    return UUID.randomUUID().toString();
}

public LocalDateTime expireTimeRange() {
    return LocalDateTime.now().plusMinutes(MINUTES);
}



}
