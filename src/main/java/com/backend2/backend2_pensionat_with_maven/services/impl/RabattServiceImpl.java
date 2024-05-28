package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.services.RabattService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.ChronoUnit.DAYS;


@Service
@RequiredArgsConstructor
public class RabattServiceImpl implements RabattService {


    public double LONG_STAY_DISCOUNT = 0.005;

    public double SUNDAY_NIGHT_DISCOUNT = 0.02;


    public double LOYAL_CUSTOMER_DISCOUNT = 0.02;

    public void setLONG_STAY_DISCOUNT(double LONG_STAY_DISCOUNT) {
        this.LONG_STAY_DISCOUNT = LONG_STAY_DISCOUNT;
    }

    public void setSUNDAY_NIGHT_DISCOUNT(double SUNDAY_NIGHT_DISCOUNT) {
        this.SUNDAY_NIGHT_DISCOUNT = SUNDAY_NIGHT_DISCOUNT;
    }


    public void setLOYAL_CUSTOMER_DISCOUNT(double LOYAL_CUSTOMER_DISCOUNT) {
        this.LOYAL_CUSTOMER_DISCOUNT = LOYAL_CUSTOMER_DISCOUNT;
    }


   // public RabattServiceImpl() {
   //     // Default values for discounts
    //    this.LONG_STAY_DISCOUNT = 0.005;
    //    this.SUNDAY_NIGHT_DISCOUNT = 0.02;
    //    this.LOYAL_CUSTOMER_DISCOUNT = 0.02;
   // }

    @Override
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

   // @Override
   // public void setRabattService(RabattService rabattService) {

   // }
}
