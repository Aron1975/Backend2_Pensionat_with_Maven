package com.backend2.backend2_pensionat_with_maven.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Data
public class PasswordResetTokenRickard {

        private static final int EXPIRATION = 60 * 24;

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String token;

        @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
        @JoinColumn(nullable = false, name = "user_id")
        private User user;

        @Column(nullable = false)
        private boolean isUsed;

        @Setter
        @Column(nullable = false)
        private LocalDateTime expireTime;

}

