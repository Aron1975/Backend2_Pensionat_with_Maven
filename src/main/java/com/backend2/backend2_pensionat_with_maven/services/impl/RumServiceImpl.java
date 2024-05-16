package com.backend2.backend2_pensionat_with_maven.services.impl;



import com.backend2.backend2_pensionat_with_maven.dtos.ContractCustomerDto;
import com.backend2.backend2_pensionat_with_maven.dtos.RumDto;
import com.backend2.backend2_pensionat_with_maven.models.Rum;
import com.backend2.backend2_pensionat_with_maven.repos.RumRepo;
import com.backend2.backend2_pensionat_with_maven.services.RumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RumServiceImpl implements RumService {

    private final RumRepo rumRepo;

    @Override
    public List<RumDto> getAllRum() {
        return rumRepo.findAll().stream().map(r -> rumToRumDto(r)).toList();
    }

    @Override
    public RumDto rumToRumDto(Rum r) {
        return RumDto.builder().id(r.getId()).nummer(r.getNummer()).rumTyp(r.getTyp()).storlek(r.getStorlek()).kapacitet(r.getKapacitet()).pris(r.getPris()).build();
    }

    @Override
    public List<RumDto> getAvailableRum(int antal) {
        switch (antal) {
            case 2: return getAllRum().stream().filter(r -> r.getKapacitet()>1).toList();
            case 3: return getAllRum().stream().filter(r -> r.getKapacitet()>2).toList();
            case 4: return getAllRum().stream().filter(r -> r.getKapacitet()>3).toList();
        }

        return getAllRum();
    }
    @Override
    public RumDto findByNummer(Long id) {
        return rumRepo.findById(id)
                .map(this::rumToRumDto)
                .orElse(null);
    }

}
