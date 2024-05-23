package com.backend2.backend2_pensionat_with_maven;

import com.backend2.backend2_pensionat_with_maven.dtos.BokningDto;
import com.backend2.backend2_pensionat_with_maven.models.Bokning;
import com.backend2.backend2_pensionat_with_maven.models.Kund;
import com.backend2.backend2_pensionat_with_maven.repos.BokningRepo;
import com.backend2.backend2_pensionat_with_maven.repos.KundRepo;
import com.backend2.backend2_pensionat_with_maven.services.BlacklistService;
import com.backend2.backend2_pensionat_with_maven.services.impl.BokningServiceImpl;
import com.backend2.backend2_pensionat_with_maven.services.impl.RabattServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BokningsServiceImplTest {

    @Mock
    private BokningRepo bokningRepo;
    @Mock
    private KundRepo kundRepo;
    @Mock
    private RabattServiceImpl rabattService;
    @Mock
    private BlacklistService blacklistService;

    @InjectMocks
    private BokningServiceImpl bokningService;

    private Bokning bokning;
    private Kund kund;
    private BokningDto bokningDto;
/*
    @BeforeEach
    public void setUp() {
        bokning = new Bokning();
        bokning.setId(1L);
        bokning.setStartDatum(LocalDate.of(2024, 5, 1));
        bokning.setSlutDatum(LocalDate.of(2024, 5, 3));
        bokning.setTotalPris(1000.0);

        kund = new Kund();
        kund.setId(1L);
        kund.setEmail("test@example.com");

        bokningDto = new BokningDto(bokning);
    } */

    @Test
    public void testUppdateraBokningMedKund_Discounts() {
        Bokning bokning = new Bokning();
        bokning.setId(1L);
        bokning.setTotalPris(1000.0);
        bokning.setStartDatum(LocalDate.of(2024, 5, 1));
        bokning.setSlutDatum(LocalDate.of(2024, 5, 10));

        Kund kund = new Kund();
        kund.setId(1L);
        kund.setEmail("test@example.com");

        BokningDto bokningDto1 = new BokningDto();
        bokningDto1.setId(1L);
        List<BokningDto> bokningDtoList = Arrays.asList(bokningDto1);



        when(bokningRepo.findById(any(Long.class))).thenReturn(Optional.of(bokning));
        when(kundRepo.findById(any(Long.class))).thenReturn(Optional.of(kund));
        when(bokningService.getAllBokningar2()).thenReturn(bokningDtoList);
        when(rabattService.calculateDiscount(
                eq(bokning.getStartDatum()),
                eq(bokning.getSlutDatum()),
                any(Integer.class))).thenReturn(0.1);
        when(rabattService.applyDiscount(any(Double.class), any(Double.class))).thenReturn(900.0);

        boolean result = bokningService.uppdateraBokningMedKund("1");

        assertEquals(true, result);
        assertEquals(900.0, bokning.getTotalPris());


    }


}
