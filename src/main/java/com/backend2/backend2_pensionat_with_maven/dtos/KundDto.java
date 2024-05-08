package com.backend2.backend2_pensionat_with_maven.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KundDto {

    private long id;

    @NotBlank(message = "Ange ditt personnummer")
    @Pattern(regexp="(^$|[0-9]{10,12})", message= "Personnumret måste vara mellan 10 och 12 siffror")
    private String ssn;

    @NotBlank(message = "Ange ditt förnamn")
    @Pattern(regexp = "(^$|^[A-Za-zåäöÅÄÖ]+$)", message = "Förnamnet får endast innehålla bokstäver")
    @Size(max = 50, message = "Förnamnet får ha maximalt 50 tecken")
    private String förnamn;

    @NotBlank(message = "Ange ditt efternamn")
    @Pattern(regexp = "(^$|^[A-Za-zåäöÅÄÖ]+$)", message = "Efternamnet får endast innehålla bokstäver")
    @Size(max = 50, message = "Efternamnet får ha maximalt 50 tecken")
    private String efternamn;
    }

