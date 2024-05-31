package com.backend2.backend2_pensionat_with_maven.Security;


import lombok.Data;

@Data
public class PasswordResetRequest {
    //private String email;
    private String newPassword;
    private String confirmPassword;
}
