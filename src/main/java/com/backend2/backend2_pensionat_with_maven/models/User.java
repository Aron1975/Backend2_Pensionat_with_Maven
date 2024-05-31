package com.backend2.backend2_pensionat_with_maven.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "User")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name="id")
    private UUID id;

    private String username;
    private String password;
    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;
}
