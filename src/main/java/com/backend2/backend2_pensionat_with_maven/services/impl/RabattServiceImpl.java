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

    private static final double LONG_STAY_DISCOUNT = 0.005;
    private static final double SUNDAY_NIGHT_DISCOUNT = 0.02;
    private static final double LOYAL_CUSTOMER_DISCOUNT = 0.02;

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
}
