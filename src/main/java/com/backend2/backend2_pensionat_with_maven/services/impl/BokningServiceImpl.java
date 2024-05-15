package com.backend2.backend2_pensionat_with_maven.services.impl;


import com.backend2.backend2_pensionat_with_maven.dtos.*;
import com.backend2.backend2_pensionat_with_maven.models.Bokning;
import com.backend2.backend2_pensionat_with_maven.models.Kund;
import com.backend2.backend2_pensionat_with_maven.models.Rum;
import com.backend2.backend2_pensionat_with_maven.repos.BokningRepo;
import com.backend2.backend2_pensionat_with_maven.repos.KundRepo;
import com.backend2.backend2_pensionat_with_maven.repos.RumRepo;
import com.backend2.backend2_pensionat_with_maven.services.BlacklistService;
import com.backend2.backend2_pensionat_with_maven.services.BokningService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class BokningServiceImpl implements BokningService {

    private final BokningRepo bokningRepo;
    private final KundRepo kundRepo;
    private final RumRepo rumRepo;
    private final BlacklistService blacklistService;

    @Override
    public List<DetailedBokningDto> getAllBokningar() {
        return bokningRepo.findAll().stream().map(b -> bokningToDetailedBokningDto(b)).toList();
    }

    @Override
    public boolean uppdateraBokningMedKund(String kundId) {
        List<BokningDto> responseList = getAllBokningar2();
        BokningDto senasteBokning = responseList.get(responseList.size() - 1);
        Long bokningsId = senasteBokning.getId();
        Long kundIdLong = Long.parseLong(kundId);
        Bokning bokning = bokningRepo.findById(bokningsId).get();
        Kund kund = kundRepo.findById(kundIdLong).get();

        String kundEmail = kund.getEmail();
        boolean isBlacklisted = isCustomerBlacklisted(kundEmail);

        if (!isBlacklisted) {
            bokning.setKund(kund);
            bokningRepo.save(bokning);
            return true;
        } else {
            System.out.println("SVARTLISTAD KUND!");
            return false;
        }
    }

    private boolean isCustomerBlacklisted(String email)  {
        try {
            List<BlacklistedCustomerDto> blacklist = blacklistService.getAllBlacklists();
            return blacklist.stream().anyMatch(blacklistDto -> blacklistDto.getEmail().equals(email) && !blacklistDto.isOk());
        } catch (Exception e) {
            System.out.println("Nåt gick fel");
        }
        return false;
    }

    @Override
    public void uppdateraBokning(String id, int antal, String startDatum, String stopDatum, long bokningsId) {
        LocalDate inch = LocalDate.parse(startDatum);
        LocalDate utch = LocalDate.parse(stopDatum);
        Long rumId = Long.parseLong(id);
        Rum rum = rumRepo.findById(rumId).get();

        Bokning bokning = bokningRepo.findById(bokningsId).get();

        long antalDagar = DAYS.between(inch, utch);
        int antalExtraSängar = (antal == 1) ? 0 : antal - 2;

        bokning.setRum(rum);
        bokning.setStartDatum(inch);
        bokning.setSlutDatum(utch);
        bokning.setAntalGäster(antal);
        bokning.setAntalExtraSängar(antalExtraSängar);
        bokning.setTotalPris(rum.getPris()*antalDagar);

        bokningRepo.save(bokning);
    }


    @Override
    public void deleteBokningWithoutKundId() {
        List<Bokning> bokningar = bokningRepo.findAll();
        for (Bokning bokning : bokningar) {
            if (bokning.getKund() == null) {
                bokningRepo.delete(bokning);
            }
        }
    }


    @Override
    public List<BokningDto> getAllBokningar2() {
        return bokningRepo.findAll().stream().map(b -> bokningToBokningDto(b)).toList();
    }

    @Override
    public BokningDto bokningToBokningDto(Bokning b) {
        return BokningDto.builder().id(b.getId()).bokningsDatum(b.getBokningsDatum()).startDatum(b.getStartDatum())
                .slutDatum(b.getSlutDatum()).antalGäster(b.getAntalGäster()).antalExtraSängar(b.getAntalExtraSängar()).totalPris(b.getTotalPris())
                .rum(new RumDto(b.getRum().getId(), b.getRum().getTyp(),b.getRum().getPris(), b.getRum().getStorlek(), b.getRum().getKapacitet(), b.getRum().getNummer())).build();

    }

    @Override
    public DetailedBokningDto bokningToDetailedBokningDto(Bokning b) {
        return DetailedBokningDto.builder().id(b.getId()).bokningsDatum(b.getBokningsDatum()).startDatum(b.getStartDatum())
                .slutDatum(b.getSlutDatum()).antalGäster(b.getAntalGäster()).antalExtraSängar(b.getAntalExtraSängar())
                .totalPris(b.getTotalPris()).kund(new KundDto(b.getKund().getId(), b.getKund().getSsn(), b.getKund().getFörnamn(), b.getKund().getEfternamn()))
                .rum(new RumDto(b.getRum().getId(), b.getRum().getTyp(),b.getRum().getPris(), b.getRum().getStorlek(), b.getRum().getKapacitet(), b.getRum().getNummer())).build();

    }

    @Override
    public List<RumDto> getAvailableRumByDate(List<RumDto> availableRumByCapacity, LocalDate startDate, LocalDate stopDate) {
        List<RumDto> availableRumByDate = new ArrayList<>();
        List<BokningDto> allaBokningar = getAllBokningar2();
        boolean isBooked = false;
        if (getAllBokningar2().isEmpty()) {
            availableRumByDate = availableRumByCapacity;
        }
        else {
            for (RumDto r : availableRumByCapacity) {
                for (BokningDto b : allaBokningar) {
                    if (r.getId() == b.getRum().getId()) {
                        if ((startDate.isBefore(b.getSlutDatum()) && stopDate.isAfter(b.getStartDatum()))) {
                            isBooked = true;
                        }
                    }
                }
                if (!isBooked) {
                    availableRumByDate.add(r);
                }
                isBooked = false;
            }
        }
        return availableRumByDate;
    }

    @Override
    public List<RumDto> getAvailableRumByDate2(List<RumDto> availableRumByCapacity, LocalDate startDate, LocalDate stopDate, long id) {
        List<RumDto> availableRumByDate = new ArrayList<>();
        List<DetailedBokningDto> allaBokningar = getAllBokningar();
        boolean isBooked = false;
        if (getAllBokningar2().isEmpty()) {
            availableRumByDate = availableRumByCapacity;
        }
        else {
            for (RumDto r : availableRumByCapacity) {
                for (DetailedBokningDto b : allaBokningar) {
                    if (r.getId() == b.getRum().getId()) {
                        if ((startDate.isBefore(b.getSlutDatum()) && stopDate.isAfter(b.getStartDatum()))) {
                            if(b.getId() != id){
                                    isBooked = true;
                            }
                        }
                    }
                }
                if (!isBooked) {
                    availableRumByDate.add(r);
                }
                isBooked = false;
            }
        }
        return availableRumByDate;
    }

    @Override
    public void sparaBokning(String id, int antal, String startDatum, String stopDatum) {
        Long rumId = Long.parseLong(id);
        Rum rum = rumRepo.findById(rumId).get();

        LocalDate inch = LocalDate.parse(startDatum);
        LocalDate utch = LocalDate.parse(stopDatum);
        long antalDagar = DAYS.between(inch, utch);
        int antalExtraSängar = (antal == 1) ? 0 : antal - 2;

        Bokning bokning = Bokning.builder()
                .bokningsDatum(LocalDate.now())
                .startDatum(inch)
                .slutDatum(utch)
                .antalGäster(antal)
                .antalExtraSängar(antalExtraSängar)
                .totalPris(rum.getPris() * antalDagar)
                .rum(rum)
                .build();

        bokningRepo.save(bokning);
    }

    @Override
    public void sparaBokningTillKund(DetailedBokningDto b) {

    }
}
