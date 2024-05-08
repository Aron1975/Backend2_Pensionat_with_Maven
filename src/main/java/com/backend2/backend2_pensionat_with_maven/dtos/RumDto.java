package com.backend2.backend2_pensionat_with_maven.dtos;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RumDto {
    private long id;

    @NotBlank(message = "Rumstyp får inte vara tom.")
    private String rumTyp;

    @DecimalMin(value = "500.00", message = "Priset måste vara minst 500 kr per natt.")
    private double pris;

    @Min(value = 10, message = "Storleken måste vara minst 10 kvm.")
    @Max(value = 25, message = "Storleken måste vara max 25 kvm.")
    private int storlek;

    @Min(value = 1, message = "Kapaciteten måste vara minst 1.")
    @Max(value = 4, message = "Kapaciteten måste vara max 4.")
    private int kapacitet;

    @Min(value = 1, message = "Rummets nummer måste vara större än 0.")
    private int nummer;
}
