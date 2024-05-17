package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.services.RabattService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
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
        long antalNätter = DAYS.between(startDatum, slutDatum);

        if (antalNätter >= 2) {
            totalDiscount += LONG_STAY_DISCOUNT;
        }
        if (startDatum.getDayOfWeek() == SUNDAY && slutDatum.getDayOfWeek() == MONDAY) {
            totalDiscount += SUNDAY_NIGHT_DISCOUNT;
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
