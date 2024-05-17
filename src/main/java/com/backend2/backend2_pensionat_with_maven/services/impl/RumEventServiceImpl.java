package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.dtos.RumEventDto;
import com.backend2.backend2_pensionat_with_maven.models.RumEvent;
import com.backend2.backend2_pensionat_with_maven.repos.RumEventRepo;
import com.backend2.backend2_pensionat_with_maven.services.RumEventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RumEventServiceImpl implements RumEventService {

    private final RumEventRepo rumEventRepo;

    @Override
    public String sparaRumEvent(String message) throws JsonProcessingException {

        System.out.println(" I spara RumEvent.....");


        RumEvent event = new ObjectMapper()
                .readerFor(RumEvent.class)
                .readValue(message);

        System.out.println("RumEventDto type: " + event ); //Dto.RumEventTypeDto.type + " Timestamp: " + eventDto.RumEventTypeDto.TimeStamp);
        //rumEventRepo.save(rumEvent);
        return "Event saved";
    }
}
