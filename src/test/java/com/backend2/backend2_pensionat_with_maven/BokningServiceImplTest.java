package com.backend2.backend2_pensionat_with_maven;


import com.backend2.backend2_pensionat_with_maven.dtos.BokningDto;
import com.backend2.backend2_pensionat_with_maven.dtos.DetailedBokningDto;
import com.backend2.backend2_pensionat_with_maven.dtos.RumDto;
import com.backend2.backend2_pensionat_with_maven.models.Bokning;
import com.backend2.backend2_pensionat_with_maven.models.Kund;
import com.backend2.backend2_pensionat_with_maven.repos.BokningRepo;
import com.backend2.backend2_pensionat_with_maven.repos.KundRepo;
import com.backend2.backend2_pensionat_with_maven.repos.RumRepo;
import com.backend2.backend2_pensionat_with_maven.services.BlacklistService;
import com.backend2.backend2_pensionat_with_maven.services.BokningService;
import com.backend2.backend2_pensionat_with_maven.services.RabattService;
import com.backend2.backend2_pensionat_with_maven.services.impl.BokningServiceImpl;
import com.backend2.backend2_pensionat_with_maven.services.impl.RabattServiceImpl;
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
import java.util.Arrays;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
@SpringBootTest
class BokningServiceImplTest {


    @Mock
    public RabattService rabattService;
    @Mock
    public Bokning bokningMock;
    @Mock
    public Kund kundMock;

    @Autowired
    @InjectMocks
    public BokningServiceImpl bokningService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        LocalDate startDatum = LocalDate.of(2024, 5, 26);
        LocalDate slutDatum = LocalDate.of(2024, 5, 28);

        when(bokningMock.getStartDatum()).thenReturn(startDatum);
        when(bokningMock.getSlutDatum()).thenReturn(slutDatum);
        when(bokningMock.getTotalPris()).thenReturn(1000.0);

        Bokning previousBokning = mock(Bokning.class);
        doReturn(LocalDate.now().minusMonths(1)).when(previousBokning).getStartDatum();
        doReturn(LocalDate.now().minusMonths(1).plusDays(12)).when(previousBokning).getSlutDatum();

        List<Bokning> bokningList = Arrays.asList(previousBokning);
        when(kundMock.getBokningList()).thenReturn(bokningList);

        int antalNätterUnderÅret = (int) DAYS.between(previousBokning.getStartDatum(), previousBokning.getSlutDatum());

        bokningService = Mockito.spy(bokningService);
        doReturn(antalNätterUnderÅret).when(bokningService).getTotalNätterUnderÅret(kundMock);
    }

    @Test
    void testUpdateBokningWithDiscount() {
        double expectedPrisEfterDiscount = 955.0d;
        double totalDiscount = 0.045;

        when(rabattService.calculateDiscount(any(LocalDate.class), any(LocalDate.class), anyInt())).thenReturn(totalDiscount);
        when(rabattService.applyDiscount(anyDouble(), anyDouble())).thenReturn(expectedPrisEfterDiscount);

        double result = bokningService.updateBokningWithDiscount(bokningMock, kundMock);

        assertEquals(expectedPrisEfterDiscount, result, 0.001);
    }
}
