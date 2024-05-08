package com.backend2.backend2_pensionat_with_maven.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BokningDto {

    private long id;

    @NotNull(message = "Bokningsdatum får inte vara null.")
    @FutureOrPresent(message = "Bokningsdatum måste vara idag eller i framtiden.")
    private LocalDate bokningsDatum;

    @NotNull(message = "Startdatum får inte vara null.")
    @FutureOrPresent(message = "Startdatum måste vara idag eller i framtiden.")
    private LocalDate startDatum;

    @NotNull(message = "Slutdatum får inte vara null.")
    private LocalDate slutDatum;

    @Min(value = 1, message = "Antal gäster måste vara minst 1.")
    @Max(value = 4, message = "Antal gäster måste vara max 4.")
    private int antalGäster;

    @Min(value = 0, message = "Antal extrasängar kan inte vara mindre än 0.")
    @Max(value = 2, message = "Antal extrasängar måste vara max 2.")
    private int antalExtraSängar;

    @Positive(message = "Totalpriset måste vara positivt.")
    private double totalPris;

    private RumDto rum;
}
