package com.backend2.backend2_pensionat_with_maven.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RumEvent {

    @Id
    @GeneratedValue
    private long id;

    private int nummer;

    private Timestamp timestamp;
}
