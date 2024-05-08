package com.backend2.backend2_pensionat_with_maven.services;



import com.backend2.backend2_pensionat_with_maven.dtos.RumDto;
import com.backend2.backend2_pensionat_with_maven.models.Rum;

import java.util.List;

public interface RumService {

    public List<RumDto> getAllRum();

    public RumDto rumToRumDto(Rum r);

    public List<RumDto> getAvailableRum(int antal);

    
}
