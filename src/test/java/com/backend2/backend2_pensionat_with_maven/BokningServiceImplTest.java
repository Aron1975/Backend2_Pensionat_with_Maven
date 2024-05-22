package com.backend2.backend2_pensionat_with_maven;


import com.backend2.backend2_pensionat_with_maven.dtos.BokningDto;
import com.backend2.backend2_pensionat_with_maven.dtos.DetailedBokningDto;
import com.backend2.backend2_pensionat_with_maven.dtos.RumDto;
import com.backend2.backend2_pensionat_with_maven.models.Bokning;
import com.backend2.backend2_pensionat_with_maven.models.Kund;
import com.backend2.backend2_pensionat_with_maven.services.BokningService;
import com.backend2.backend2_pensionat_with_maven.services.RabattService;
import com.backend2.backend2_pensionat_with_maven.services.impl.BokningServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BokningServiceImplTest {

//    @Mock
//    private RabattService rabattService;
//
//    @Spy
//    @InjectMocks
//    private BokningServiceImpl bokningService;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//    }


    @Autowired
    private RabattService rabattService;

    @Mock
    private Bokning bokningMock;
    @Mock
    private Kund kundMock;
    @Mock
    private BokningService bokningService;

    @Test
    public void testUpdateBokningWithDiscount() {
        // Förberedning av testdata
        LocalDate startDatum = LocalDate.now();
        LocalDate slutDatum = startDatum.plusDays(2);
        bokningMock = mock(Bokning.class);
        bokningMock.setStartDatum(startDatum);
        bokningMock.setSlutDatum(slutDatum);
        bokningMock.setTotalPris(1000.0);

        kundMock = mock(Kund.class);
        int antalNätterUnderÅret = 12;

        double expectedDiscount = 0.02 + 0.005;
        double expectedPrisEfterDiscount = 1000.0 * (1 - expectedDiscount);

        // Stubba metodanrop
        when(bokningService.getTotalNätterUnderÅret(any())).thenReturn(antalNätterUnderÅret);
        when(rabattService.calculateDiscount(eq(startDatum), eq(slutDatum), anyInt())).thenReturn(expectedDiscount);
        when(rabattService.applyDiscount(anyDouble(), anyDouble())).thenReturn(expectedPrisEfterDiscount);

        // Anropa metoden som ska testas
        bokningService.updateBokningWithDiscount(bokningMock, kundMock);

        // Kontrollera att det rabatterade priset är korrekt
        assertEquals(expectedPrisEfterDiscount, bokningMock.getTotalPris(), 0.001);
        // Verifiera att rabattService-metoderna anropas med korrekta argument
        verify(rabattService).calculateDiscount(startDatum, slutDatum, antalNätterUnderÅret);
        verify(rabattService).applyDiscount(1000.0, expectedDiscount);
    }
}





