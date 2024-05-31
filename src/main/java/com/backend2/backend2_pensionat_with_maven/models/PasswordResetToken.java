package com.backend2.backend2_pensionat_with_maven.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
    public class PasswordResetToken {

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

        @Column(nullable = false)
        private LocalDateTime expireTime;


    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }


}

