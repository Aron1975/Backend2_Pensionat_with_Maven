package com.backend2.backend2_pensionat_with_maven.services;



import com.backend2.backend2_pensionat_with_maven.dtos.DetailedKundDto;
import com.backend2.backend2_pensionat_with_maven.dtos.KundDto;
import com.backend2.backend2_pensionat_with_maven.models.Kund;

import java.util.List;

public interface KundService {


    
    public List<DetailedKundDto> getAllKunder();

    public DetailedKundDto kundToDetailedKundDto(Kund k);

    public Kund detailedKundDtoToKund(DetailedKundDto k);

    public KundDto kundToKundDto(Kund k);

    public void deleteKundById(long id);

    public String spara(DetailedKundDto k);

    public DetailedKundDto getKund(Integer id);




}
