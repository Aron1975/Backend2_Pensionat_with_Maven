package com.backend2.backend2_pensionat_with_maven.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Ange din e-postadress")
    @Email(message = "Användarnamn måste vara en giltig e-postadress")
    @Column(nullable = false)
    private String username;

    @NotBlank(message = "Ange lösenord")
    @Size(min = 2, message = "Lösenordet måste ha minst 2 tecken")
    @Column(nullable = false)
    private String password;
    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;
}