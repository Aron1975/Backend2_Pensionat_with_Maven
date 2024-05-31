package com.backend2.backend2_pensionat_with_maven.services;

import java.time.LocalDateTime;

public interface ForgotPasswordService {

    public String generateToken();

    public LocalDateTime expireTimeRange();


}
