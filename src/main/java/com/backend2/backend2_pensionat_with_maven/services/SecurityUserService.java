package com.backend2.backend2_pensionat_with_maven.services;

public interface SecurityUserService {

    String validatePasswordResetToken(String token);

}
