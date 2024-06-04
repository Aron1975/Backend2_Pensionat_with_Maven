package com.backend2.backend2_pensionat_with_maven.repos;

import com.backend2.backend2_pensionat_with_maven.Security.PasswordResetToken;
import com.backend2.backend2_pensionat_with_maven.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String theToken);
    public PasswordResetToken findPasswordResetTokenByUser(User user);
}