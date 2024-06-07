package com.backend2.backend2_pensionat_with_maven.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private UUID id;
    //private Long id;
    @NotBlank(message = "Ange din e-postadress")
    @Email(message = "Användarnamn måste vara en giltig e-postadress")
    private String username;

    @NotBlank(message = "Ange lösenord")
    @Size(min = 2, message = "Lösenordet måste ha minst 2 tecken")
    private String password;
    @NotEmpty(message = "Ange roll")
    private Set<String> roles;
}
