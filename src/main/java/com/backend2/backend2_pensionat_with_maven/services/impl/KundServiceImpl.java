package com.backend2.backend2_pensionat_with_maven.services.impl;


import com.backend2.backend2_pensionat_with_maven.dtos.DetailedKundDto;
import com.backend2.backend2_pensionat_with_maven.dtos.KundDto;
import com.backend2.backend2_pensionat_with_maven.models.Kund;
import com.backend2.backend2_pensionat_with_maven.repos.BokningRepo;
import com.backend2.backend2_pensionat_with_maven.repos.KundRepo;
import com.backend2.backend2_pensionat_with_maven.services.KundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KundServiceImpl implements KundService {

    private final KundRepo kundRepo;
    private final BokningRepo bokningsRepo;


    @Override
    public List<DetailedKundDto> getAllKunder() {
        return kundRepo.findAll().stream().map(k -> kundToDetailedKundDto(k)).toList();
    }


    @Override
    public DetailedKundDto kundToDetailedKundDto(Kund k) {
        return DetailedKundDto.builder().id(k.getId()).ssn(k.getSsn()).förnamn(k.getFörnamn())
                .efternamn(k.getEfternamn()).mobilnummer(k.getMobilnummer()).email(k.getEmail())
                .adress(k.getAdress()).stad(k.getStad()).build();
    }

    @Override
    public Kund detailedKundDtoToKund(DetailedKundDto dto) {
        Kund kund = new Kund();
        kund.setId(dto.getId());
        kund.setSsn(dto.getSsn());
        kund.setFörnamn(dto.getFörnamn());
        kund.setEfternamn(dto.getEfternamn());
        kund.setAdress(dto.getAdress());
        kund.setStad(dto.getStad());
        kund.setMobilnummer(dto.getMobilnummer());
        kund.setEmail(dto.getEmail());
        return kund;
    }

    public String spara(DetailedKundDto k){
        Kund kund = detailedKundDtoToKund(k);
        kundRepo.save(kund);
       String hej = "hej där";   //för test skull
       return hej;

    }

    @Override
    public void deleteKundById(long id) {
        Kund kundToDelete = kundRepo.findById(id).orElse(null);
        if (kundToDelete != null && !checkIfKundHasBokning(id)) {
            kundRepo.deleteById(id);
        }
    }

    public DetailedKundDto getKund(Integer id) {
        Kund kund = kundRepo.findById(Long.valueOf(id)).orElse(null);
        if (kund != null) {
            return kundToDetailedKundDto(kund);
        }
        return null;
    }


    @Override
    public KundDto kundToKundDto(Kund k) {
        return KundDto.builder().id(k.getId()).ssn(k.getSsn()).förnamn(k.getFörnamn()).efternamn(k.getEfternamn())
                .build();
    }


    public boolean checkIfKundHasBokning(long kundId){
        return bokningsRepo.getKundIdList().stream().anyMatch(kund -> kund.getId() == kundId);
    }

}
