package com.backend2.backend2_pensionat_with_maven.services;



import com.backend2.backend2_pensionat_with_maven.dtos.BokningDto;
import com.backend2.backend2_pensionat_with_maven.dtos.DetailedBokningDto;
import com.backend2.backend2_pensionat_with_maven.dtos.RumDto;
import com.backend2.backend2_pensionat_with_maven.models.Bokning;
import com.backend2.backend2_pensionat_with_maven.models.Kund;

import java.time.LocalDate;
import java.util.List;

public interface BokningService {

    public List<DetailedBokningDto> getAllBokningar();

    public boolean uppdateraBokningMedKund(String kundId);
    public void deleteBokningWithoutKundId();
    public List<BokningDto> getAllBokningar2();

    BokningDto bokningToBokningDto(Bokning b);

    Bokning bokningDtoToBokning(BokningDto b);

    //public void deleteNullBokning(Bokning b);
    public DetailedBokningDto bokningToDetailedBokningDto(Bokning b);
    public List<RumDto> getAvailableRumByDate(List<RumDto> availableRumByCapacity, LocalDate startDate, LocalDate stopDate);
    public List<RumDto> getAvailableRumByDate2(List<RumDto> availableRumByCapacity, LocalDate startDate, LocalDate stopDate, long id);

    public void updateBokningWithDiscount(Bokning bokning, Kund kund);

    public int getTotalNätterUnderÅret(Kund kund);

    void uppdateraBokning(String id, int antal, String startDatum, String stopDatum, long bokningsId);

    void sparaBokning(String id, int antal, String startDatum, String stopDatum);
    void sparaBokningTillKund(DetailedBokningDto b);
}
