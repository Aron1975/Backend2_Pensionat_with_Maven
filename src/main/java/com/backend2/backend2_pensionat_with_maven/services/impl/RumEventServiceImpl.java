package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.dtos.RumEventDto;
import com.backend2.backend2_pensionat_with_maven.models.RumEvent;
import com.backend2.backend2_pensionat_with_maven.repos.RumEventRepo;
import com.backend2.backend2_pensionat_with_maven.services.RumEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RumEventServiceImpl implements RumEventService {

    private final RumEventRepo rumEventRepo;

    @Override
    public String sparaRumEvent(RumEventDto rumEventDto) {

        System.out.println("RumEventDto.RumEventTypeDto.class: " + RumEventDto.RumEventTypeDto.class);
        //rumEventRepo.save(rumEvent);
        return "Event saved";
    }
}
