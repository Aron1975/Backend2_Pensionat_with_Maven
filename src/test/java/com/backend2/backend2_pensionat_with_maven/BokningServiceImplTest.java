package com.backend2.backend2_pensionat_with_maven;


import com.backend2.backend2_pensionat_with_maven.dtos.BlacklistedCustomerDto;
import com.backend2.backend2_pensionat_with_maven.dtos.BokningDto;
import com.backend2.backend2_pensionat_with_maven.dtos.RumDto;
import com.backend2.backend2_pensionat_with_maven.models.Bokning;
import com.backend2.backend2_pensionat_with_maven.models.Kund;
import com.backend2.backend2_pensionat_with_maven.models.Rum;
import com.backend2.backend2_pensionat_with_maven.repos.BokningRepo;
import com.backend2.backend2_pensionat_with_maven.repos.KundRepo;
import com.backend2.backend2_pensionat_with_maven.services.BlacklistService;
import com.backend2.backend2_pensionat_with_maven.services.RabattService;
import com.backend2.backend2_pensionat_with_maven.services.impl.BokningServiceImpl;
import com.backend2.backend2_pensionat_with_maven.services.impl.RabattServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BokningServiceImplTest {
    @Mock
    private BokningRepo bokningRepo;

    @Mock
    private KundRepo kundRepo;

    @Mock
    private BlacklistService blacklistService;

    @Mock
    private RabattService rabattService;

    @InjectMocks
    private BokningServiceImpl bokningService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUppdateraBokningMedKund() throws IOException {
        // Mocka data
        Rum mockRum = new Rum();
        mockRum.setId(1L);
        mockRum.setPris(500.0);
        mockRum.setNummer(1);
        mockRum.setStorlek(10);
        mockRum.setKapacitet(1);

        BokningDto mockBokningDto = new BokningDto();
        mockBokningDto.setId(1L);
        mockBokningDto.setStartDatum(LocalDate.of(2024, 6, 5));
        mockBokningDto.setSlutDatum(LocalDate.of(2024, 6, 14));
        mockBokningDto.setBokningsDatum(LocalDate.of(2024, 5, 22));
        mockBokningDto.setAntalGäster(1);
        mockBokningDto.setTotalPris(4500.0);
        mockBokningDto.setAntalExtraSängar(0);


//        List<Bokning> bokningarList = new ArrayList<>();
//        Bokning bokning = new Bokning();
//        bokning.setId(1L);
//        bokning.setStartDatum(LocalDate.of(2024, 6, 5));
//        bokning.setSlutDatum(LocalDate.of(2024, 6, 14));
//        bokning.setBokningsDatum(LocalDate.of(2024, 5, 22));
//        bokning.setAntalGäster(1);
//        bokning.setTotalPris(4500.0);
//        bokning.setAntalExtraSängar(0);
//        bokning.setRum(mockRum);
//        bokningarList.add(bokning);

        List<BokningDto> bokningarDto = new ArrayList<>();
        bokningarDto.add(mockBokningDto);

        Kund mockKund = new Kund();
        mockKund.setId(1L);
        mockKund.setEmail("test@example.com");


//        BokningDto bokningDto = new BokningDto();
//        bokningDto.setId(1L);
//        bokningDto.setStartDatum(LocalDate.of(2024, 6, 5));
//        bokningDto.setSlutDatum(LocalDate.of(2024, 6, 14));
//        bokningDto.setBokningsDatum(LocalDate.of(2024, 5, 22));
//        bokningDto.setAntalGäster(1);
//        bokningDto.setTotalPris(4500.0);
//        bokningDto.setAntalExtraSängar(0);


//        List<Bokning> mockResponseList = new ArrayList<>();
//        mockResponseList.add(mockBokning);

        // Mocka anropen till repository och andra beroenden
        when(bokningRepo.findAll()).thenReturn(Collections.singletonList(bokningService.bokningDtoToBokning(mockBokningDto)));
        when(kundRepo.findById(anyLong())).thenReturn(Optional.of(mockKund));
        when(bokningService.getAllBokningar2()).thenReturn(bokningarDto);
        when(rabattService.calculateDiscount(any(LocalDate.class), any(LocalDate.class), anyInt())).thenReturn(0.025);
        when(rabattService.applyDiscount(anyDouble(), anyDouble())).thenReturn(4387.5);

        // Utför testet
        boolean result = bokningService.uppdateraBokningMedKund("1");

        // Verifiera resultatet
        Assertions.assertTrue(result);
        Assertions.assertEquals(4387.5, mockBokningDto.getTotalPris(), 0.001);
        //verify(bokningRepo, times(1)).save(bokningar);
    }

}





