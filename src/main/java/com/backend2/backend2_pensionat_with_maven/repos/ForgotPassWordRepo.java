package com.backend2.backend2_pensionat_with_maven.repos;

import com.backend2.backend2_pensionat_with_maven.models.PasswordResetTokenRickard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ForgotPassWordRepo extends JpaRepository<PasswordResetTokenRickard, UUID> {



}
