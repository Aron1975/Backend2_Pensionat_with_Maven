package com.backend2.backend2_pensionat_with_maven.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Blacklist {

    @Id
    @GeneratedValue
    public int id;
    public int blacklistId;
    public String email;
    public String name;
    public String group;
    public Date created;
    public boolean ok;
}
