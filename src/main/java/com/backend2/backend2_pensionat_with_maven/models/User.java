package com.backend2.backend2_pensionat_with_maven.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.UUID;

@Builder
@Entity
@Table(name = "User")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name="id")
    private UUID id;

    @OneToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;

    private String email;
    private String password;
    private boolean enabled;

    public String getEmail(){
        return email;
    }

    public void setPassword(final String password) {
        this.password = password;
    }


}

