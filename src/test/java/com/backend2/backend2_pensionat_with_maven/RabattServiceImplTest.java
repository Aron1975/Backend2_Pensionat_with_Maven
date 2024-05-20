package com.backend2.backend2_pensionat_with_maven;


import com.backend2.backend2_pensionat_with_maven.services.impl.RabattServiceImpl;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RabattServiceImplTest {

    private final RabattServiceImpl rabattService = new RabattServiceImpl();

    @Test
    public void testCalculateDiscount_LongStay() {
        LocalDate startDatum = LocalDate.of(2024, 5, 1);
        LocalDate slutDatum = LocalDate.of(2024, 5, 4); //3 nätter

        double discount = rabattService.calculateDiscount(startDatum, slutDatum, 0);

        assertEquals(0.005, discount, 0.001);
    }

    @Test
    public void testCalculateDiscount_SundayNight() {
        LocalDate startDatum = LocalDate.of(2024, 5, 5); //Söndag
        LocalDate slutDatum = LocalDate.of(2024, 5, 6); //Måndag

        double discount = rabattService.calculateDiscount(startDatum, slutDatum, 0);

        assertEquals(0.02, discount, 0.001);
    }

    @Test
    public void testCalculateDiscount_LoyalCustomer() {
        LocalDate startDatum = LocalDate.of(2024, 5, 1);
        LocalDate slutDatum = LocalDate.of(2024, 5, 2); //1 natt

        double discount = rabattService.calculateDiscount(startDatum, slutDatum, 10);

        assertEquals(0.02, discount, 0.001);
    }

//    @Test
//    public void testCalculateDiscount_AllDiscounts() {
//        LocalDate startDatum = LocalDate.of(2024, 5, 2);//Torsdag
//        LocalDate slutDatum = LocalDate.of(2024, 5, 7); //Tisdag, inklusive en söndag
//
//        double discount = rabattService.calculateDiscount(startDatum, slutDatum, 10);
//        assertEquals(0.045, discount, 0.001);
//    }

//    @Test
//    public void testApplyDiscount() {
//        double totalPris = 1000.0;
//        double discount = 0.05;
//
//        double discountedPris = rabattService.applyDiscount(totalPris, discount);
//        assertEquals(950.0, discountedPris, 0.001);
//    }

//    @Test
//    public void testCalculateDiscount_NoDiscounts() {
//        LocalDate startDatum = LocalDate.of(2024, 5, 1);
//        LocalDate slutDatum = LocalDate.of(2024, 5, 2); // 1 natt
//
//        double discount = rabattService.calculateDiscount(startDatum, slutDatum, 5);
//        assertEquals(0.0, discount, 0.001);
//    }

}
