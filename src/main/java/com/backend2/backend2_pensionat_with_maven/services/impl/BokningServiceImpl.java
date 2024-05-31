package com.backend2.backend2_pensionat_with_maven.services.impl;


import com.backend2.backend2_pensionat_with_maven.configuration.IntegrationProperties;
import com.backend2.backend2_pensionat_with_maven.dtos.*;
import com.backend2.backend2_pensionat_with_maven.models.Bokning;
import com.backend2.backend2_pensionat_with_maven.models.Kund;
import com.backend2.backend2_pensionat_with_maven.models.Rum;
import com.backend2.backend2_pensionat_with_maven.repos.BokningRepo;
import com.backend2.backend2_pensionat_with_maven.repos.KundRepo;
import com.backend2.backend2_pensionat_with_maven.repos.RumRepo;
import com.backend2.backend2_pensionat_with_maven.services.BlacklistService;
import com.backend2.backend2_pensionat_with_maven.services.BokningService;
import com.backend2.backend2_pensionat_with_maven.services.RabattService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
//@RequiredArgsConstructor
public class BokningServiceImpl implements BokningService {

    public final BokningRepo bokningRepo;
    public final KundRepo kundRepo;
    public final RumRepo rumRepo;
    public final BlacklistService blacklistService;
    public final RabattService rabattService;
    private EmailServiceImpl emailService;

    //@Autowired
    public BokningServiceImpl(BokningRepo bokningRepo, KundRepo kundRepo, RumRepo rumRepo,
                              BlacklistService blacklistService, RabattService rabattService) {
        this.bokningRepo = bokningRepo;
        this.kundRepo = kundRepo;
        this.rumRepo = rumRepo;
        this.blacklistService = blacklistService;
        this.rabattService = rabattService;
    }

    @Autowired
    public BokningServiceImpl(BokningRepo bokningRepo, KundRepo kundRepo, RumRepo rumRepo,
                              BlacklistService blacklistService, RabattService rabattService, EmailServiceImpl emailService) {
        this.bokningRepo = bokningRepo;
        this.kundRepo = kundRepo;
        this.rumRepo = rumRepo;
        this.blacklistService = blacklistService;
        this.rabattService = rabattService;
        this.emailService = emailService;
    }


    @Override
    public List<DetailedBokningDto> getAllBokningar() {
        return bokningRepo.findAll().stream().map(this::bokningToDetailedBokningDto).toList();
    }

    @Override
    public boolean uppdateraBokningMedKund(String kundId) throws MessagingException {
        List<BokningDto> responseList = getAllBokningar2();

        if (responseList.isEmpty()) {
            System.out.println("Inga bokningar hittades.");
            return false;
        }

        BokningDto senasteBokning = responseList.get(responseList.size() - 1);
        Long bokningsId = senasteBokning.getId();
        Long kundIdLong = Long.parseLong(kundId);

        Optional<Bokning> optionalBokning = bokningRepo.findById(bokningsId);
        Optional<Kund> optionalKund = kundRepo.findById(kundIdLong);

        if (!optionalBokning.isPresent() || !optionalKund.isPresent()) {
            System.out.println("Ingen bokning eller kund hittades med det angivna ID:t.");
            return false;
        }

        Bokning bokning = optionalBokning.get();
        Kund kund = optionalKund.get();

        if (isCustomerBlacklisted(kund.getEmail())) {
            System.out.println("SVARTLISTAD KUND!");
            return false;
        }

        bokning.setKund(kund);
        updateBokningWithDiscount(bokning, kund);

        bokning = bokningRepo.save(bokning);
        emailService.sendConfirmationMail(bokning);

        return true;
    }

    public double updateBokningWithDiscount(Bokning bokning, Kund kund) {
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
        return prisEfterDiscount;
    }

    public int getTotalNätterUnderÅret(Kund kund) {
        LocalDate ettÅrSen = LocalDate.now().minusYears(1);
        return kund.getBokningList().stream()
                .filter(bokning -> bokning.getStartDatum().isAfter(ettÅrSen))
                .mapToInt(bokning -> (int) DAYS.between(bokning.getStartDatum(),
                        bokning.getSlutDatum())).sum();
    }

    public boolean isCustomerBlacklisted(String email)  {
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
        List<Bokning> bokningar = bokningRepo.findAll();
        return bokningar.stream().map(this::bokningToBokningDto).collect(Collectors.toList());
    }

    @Override
    public BokningDto bokningToBokningDto(Bokning b) {
        return BokningDto.builder().id(b.getId()).bokningsDatum(b.getBokningsDatum()).startDatum(b.getStartDatum())
                .slutDatum(b.getSlutDatum()).antalGäster(b.getAntalGäster()).antalExtraSängar(b.getAntalExtraSängar()).totalPris(b.getTotalPris())
                .rum(new RumDto(b.getRum().getId(), b.getRum().getTyp(),b.getRum().getPris(), b.getRum().getStorlek(), b.getRum().getKapacitet(), b.getRum().getNummer())).build();

    }

    @Override
    public Bokning bokningDtoToBokning(BokningDto b) {
        return Bokning.builder().id(b.getId()).bokningsDatum(b.getBokningsDatum()).startDatum(b.getStartDatum()).slutDatum(b.getSlutDatum())
                .antalGäster(b.getAntalGäster()).antalExtraSängar(b.getAntalExtraSängar()).totalPris(b.getTotalPris())
                .rum(new Rum()).build();
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
    public Bokning sparaBokning(String id, int antal, String startDatum, String stopDatum) {
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

        return bokningRepo.save(bokning);
    }

    @Override
    public void sparaBokningTillKund(DetailedBokningDto b) {

    }

    @Override
    public String getEmailMessage(Bokning bokning) {

        long antalNätter = ChronoUnit.DAYS.between(bokning.getStartDatum(), bokning.getSlutDatum());
        String emailMessage= String.format("Tack för din bokning! \n Välkommen till Pensionatet! \n Din bokning: \n Incheckning: %s \n Utcheckning: %s \n %d nätter \n %d gäster \n Pris: %.1f Kr", bokning.getStartDatum(), bokning.getSlutDatum(), antalNätter, bokning.getAntalGäster(), bokning.getTotalPris());

        return emailMessage.toString();
    }
}
