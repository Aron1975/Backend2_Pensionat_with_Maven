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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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


    @Mock
    private RabattService rabattService;

    @Mock
    private Bokning bokningMock;
    @Mock
    private Kund kundMock;
    @InjectMocks
    private BokningServiceImpl bokningService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);


    }


    @Test
    public void testUpdateBokningWithDiscount() {
        // Förberedning av testdata
        LocalDate startDatum = LocalDate.now();
        LocalDate slutDatum = startDatum.plusDays(2);

        Bokning previousBokning = mock(Bokning.class);
        when(previousBokning.getStartDatum()).thenReturn(LocalDate.now().minusMonths(1));
        when(previousBokning.getSlutDatum()).thenReturn(LocalDate.now().minusMonths(1).plusDays(2));
        List<Bokning> bokningList = Arrays.asList(previousBokning);
        when(kundMock.getBokningList()).thenReturn(bokningList);

        Kund kundM = mock(Kund.class);
        kundM.setId(1L);
        kundM.setEmail("test@example.com");
        kundM.setFörnamn("Pontus");
        kundM.setEfternamn("Lundin");
        kundM.setSsn("9009295412");
        kundM.setAdress("Viavägen 4");
        kundM.setStad("Rövhålet");
        kundM.setMobilnummer("0768750975");
        kundM.setBokningList(bokningList);

        when(bokningMock.getStartDatum()).thenReturn(startDatum);
        when(bokningMock.getSlutDatum()).thenReturn(slutDatum);
        when(bokningMock.getTotalPris()).thenReturn(1000.0);

       // assertNotNull(kundMock, "kundMock should not be null before method call");

        int antalNätterUnderÅret = 12;

        double expectedDiscount = 0.02 + 0.005;
        double expectedPrisEfterDiscount = 1000.0 * (1 - expectedDiscount);

        // Stubba metodanrop
        when(bokningService.getTotalNätterUnderÅret(kundM)).thenReturn(antalNätterUnderÅret);
       // when(rabattService.calculateDiscount(eq(startDatum), eq(slutDatum), anyInt())).thenReturn(expectedDiscount);
      //  when(rabattService.applyDiscount(anyDouble(), anyDouble())).thenReturn(expectedPrisEfterDiscount);
        when(rabattService.calculateDiscount(any(LocalDate.class), any(LocalDate.class), anyInt())).thenReturn(0.025d);
        when(rabattService.applyDiscount(eq(1000.0d), eq(0.025d))).thenReturn(975.0d);

        System.out.println("Expected Discount: " + expectedDiscount);
        System.out.println("Expected Price After Discount: " + expectedPrisEfterDiscount);

        // Directly calling the method with correct mocks
        bokningService.updateBokningWithDiscount(bokningMock, kundM);

        System.out.println("Actual Discount: " + rabattService.calculateDiscount(startDatum, slutDatum, antalNätterUnderÅret));
        System.out.println("Actual Price After Discount: " + bokningMock.getTotalPris());

        // Verify the discounted price is correct
        verify(bokningMock).setTotalPris(expectedPrisEfterDiscount);
        assertEquals(expectedPrisEfterDiscount, bokningMock.getTotalPris(), 0.001);

        // Verify that RabattService methods are called with correct arguments
      //  verify(rabattService).calculateDiscount(startDatum, slutDatum, antalNätterUnderÅret);
       // verify(rabattService).applyDiscount(1000.0d, 0.025d); // Ensure correct arguments are passed

/*

        BokningServiceImpl bokningServiceSpy = spy(bokningService);
        doReturn(antalNätterUnderÅret).when(bokningServiceSpy).getTotalNätterUnderÅret(kundMock);

        // Call the method to be tested
        bokningServiceSpy.updateBokningWithDiscount(bokningMock, kundMock);

        // Verify the discounted price is correct
        verify(bokningMock).setTotalPris(expectedPrisEfterDiscount);
        assertEquals(expectedPrisEfterDiscount, bokningMock.getTotalPris(), 0.001);

        // Verify that RabattService methods are called with correct arguments
        verify(rabattService).calculateDiscount(startDatum, slutDatum, antalNätterUnderÅret);
        verify(rabattService).applyDiscount(1000.0, expectedDiscount);

 */

 /*
        // Anropa metoden som ska testas
        bokningService.updateBokningWithDiscount(bokningMock, kundMock);

        verify(bokningMock).setTotalPris(expectedPrisEfterDiscount);
        // Kontrollera att det rabatterade priset är korrekt
        assertEquals(expectedPrisEfterDiscount, bokningMock.getTotalPris(), 0.001);
        // Verifiera att rabattService-metoderna anropas med korrekta argument
        verify(rabattService).calculateDiscount(startDatum, slutDatum, antalNätterUnderÅret);
        verify(rabattService).applyDiscount(1000.0, expectedDiscount);



  */
    }


    /*

        public void updateBokningWithDiscount(Bokning bokning, Kund kund) {
        LocalDate startDatum = bokning.getStartDatum();
        LocalDate slutDatum = bokning.getSlutDatum();
        int antalNätterUnderÅret = getTotalNätterUnderÅret(kund);
        System.out.println("Antal nätter under året: " + antalNätterUnderÅret);
        double discount = rabattService.calculateDiscount(startDatum, slutDatum, antalNätterUnderÅret);
        System.out.println("Discount calculated: " + discount);
        double totalPris = bokning.getTotalPris();
        System.out.println("Total price before discount: " + totalPris);
        double prisEfterDiscount = rabattService.applyDiscount(totalPris, discount);
        System.out.println("Price after discount: " + prisEfterDiscount);
        bokning.setTotalPris(prisEfterDiscount);
    }



     public int getTotalNätterUnderÅret(Kund kund) {
        LocalDate ettÅrSen = LocalDate.now().minusYears(1);
        return kund.getBokningList().stream()
                .filter(bokning -> bokning.getStartDatum().isAfter(ettÅrSen))
                .mapToInt(bokning -> (int) DAYS.between(bokning.getStartDatum(),
                        bokning.getSlutDatum())).sum();
    }

    public double calculateDiscount(LocalDate startDatum, LocalDate slutDatum, int antalNätterUnderÅret) {
        double totalDiscount = 0.0;
        long antalNätter = ChronoUnit.DAYS.between(startDatum, slutDatum);

        if (antalNätter >= 2) {
            totalDiscount += LONG_STAY_DISCOUNT;
        }
        for (LocalDate date = startDatum; !date.isAfter(slutDatum); date = date.plusDays(1)) {
            if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                totalDiscount += SUNDAY_NIGHT_DISCOUNT;
                break;
            }
        }
        if (antalNätterUnderÅret >= 10) {
            totalDiscount += LOYAL_CUSTOMER_DISCOUNT;
        }
        return totalDiscount;
    }




    @Override
    public double applyDiscount(double totalPris, double discount) {
        return totalPris * (1 - discount);
    }
}




     */
}


/*


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


    @Mock
    private RabattService rabattService;

    @Mock
    private Bokning bokningMock;
    @Mock
    private Kund kundMock;
    @InjectMocks
    private BokningServiceImpl bokningService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testUpdateBokningWithDiscount() {
        // Förberedning av testdata
        LocalDate startDatum = LocalDate.now();
        LocalDate slutDatum = startDatum.plusDays(2);

        when(bokningMock.getStartDatum()).thenReturn(startDatum);
        when(bokningMock.getSlutDatum()).thenReturn(slutDatum);
        when(bokningMock.getTotalPris()).thenReturn(1000.0);

        Bokning previousBokning = mock(Bokning.class);
        when(previousBokning.getStartDatum()).thenReturn(LocalDate.now().minusMonths(1));
        when(previousBokning.getSlutDatum()).thenReturn(LocalDate.now().minusMonths(1).plusDays(2));
        List<Bokning> bokningList = Arrays.asList(previousBokning);
        when(kundMock.getBokningList()).thenReturn(bokningList);

       // assertNotNull(kundMock, "kundMock should not be null before method call");

        int antalNätterUnderÅret = 12;

        double expectedDiscount = 0.02 + 0.005;
        double expectedPrisEfterDiscount = 1000.0 * (1 - expectedDiscount);

        // Stubba metodanrop
       // when(bokningService.getTotalNätterUnderÅret(any())).thenReturn(antalNätterUnderÅret);
       // when(rabattService.calculateDiscount(eq(startDatum), eq(slutDatum), anyInt())).thenReturn(expectedDiscount);
      //  when(rabattService.applyDiscount(anyDouble(), anyDouble())).thenReturn(expectedPrisEfterDiscount);
        when(rabattService.calculateDiscount(any(LocalDate.class), any(LocalDate.class), anyInt())).thenReturn(expectedDiscount);
        when(rabattService.applyDiscount(anyDouble(), anyDouble())).thenReturn(expectedPrisEfterDiscount);


        // Directly calling the method with correct mocks
        bokningService.updateBokningWithDiscount(bokningMock, kundMock);

        // Verify the discounted price is correct
        verify(bokningMock).setTotalPris(expectedPrisEfterDiscount);
        assertEquals(expectedPrisEfterDiscount, bokningMock.getTotalPris(), 0.001);

        // Verify that RabattService methods are called with correct arguments
        verify(rabattService).calculateDiscount(startDatum, slutDatum, antalNätterUnderÅret);
        verify(rabattService).applyDiscount(1000.0, expectedDiscount);

/*

        BokningServiceImpl bokningServiceSpy = spy(bokningService);
        doReturn(antalNätterUnderÅret).when(bokningServiceSpy).getTotalNätterUnderÅret(kundMock);

        // Call the method to be tested
        bokningServiceSpy.updateBokningWithDiscount(bokningMock, kundMock);

        // Verify the discounted price is correct
        verify(bokningMock).setTotalPris(expectedPrisEfterDiscount);
        assertEquals(expectedPrisEfterDiscount, bokningMock.getTotalPris(), 0.001);

        // Verify that RabattService methods are called with correct arguments
        verify(rabattService).calculateDiscount(startDatum, slutDatum, antalNätterUnderÅret);
        verify(rabattService).applyDiscount(1000.0, expectedDiscount);

 */

 /*
        // Anropa metoden som ska testas
        bokningService.updateBokningWithDiscount(bokningMock, kundMock);

        verify(bokningMock).setTotalPris(expectedPrisEfterDiscount);
        // Kontrollera att det rabatterade priset är korrekt
        assertEquals(expectedPrisEfterDiscount, bokningMock.getTotalPris(), 0.001);
        // Verifiera att rabattService-metoderna anropas med korrekta argument
        verify(rabattService).calculateDiscount(startDatum, slutDatum, antalNätterUnderÅret);
        verify(rabattService).applyDiscount(1000.0, expectedDiscount);



  */







