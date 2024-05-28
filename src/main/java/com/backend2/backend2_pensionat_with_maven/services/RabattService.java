package com.backend2.backend2_pensionat_with_maven.services;


import org.springframework.stereotype.Service;

import java.time.LocalDate;

public interface RabattService {

    public double calculateDiscount(LocalDate startDatum, LocalDate slutDatum, int antalNätterUnderÅret);

    public double applyDiscount(double totalPris, double discount);

    void setLONG_STAY_DISCOUNT(double LONG_STAY_DISCOUNT);

    void setSUNDAY_NIGHT_DISCOUNT(double SUNDAY_NIGHT_DISCOUNT);

    void setLOYAL_CUSTOMER_DISCOUNT(double LOYAL_CUSTOMER_DISCOUNT);


}

