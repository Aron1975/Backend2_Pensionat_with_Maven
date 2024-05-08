package com.backend2.backend2_pensionat_with_maven.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rum {

    @Id
    @GeneratedValue
    private long id;

    @Min(value = 1, message = "Rummets nummer måste vara större än 0.")
    @Column(nullable = false)
    private int nummer;

    @Min(value = 10, message = "Storleken måste vara minst 10 kvm.")
    @Max(value = 25, message = "Storleken måste vara max 25 kvm.")
    @Column(nullable = false)
    private int storlek;

    @NotBlank(message = "Typ av rum får inte vara tomt.")
    @Column(nullable = false)
    private String typ;

    @Min(value = 1, message = "Kapaciteten måste vara minst 1.")
    @Max(value = 4, message = "Kapaciteten måste vara max 4.")
    @Column(nullable = false)
    private int kapacitet;

    @DecimalMin(value = "500.00", message = "Priset måste vara minst 500 kr per natt.")
    @Column(nullable = false)
    private double pris;


    @OneToMany(mappedBy = "rum")
    private List<Bokning> bokningList = new ArrayList<>();
}
