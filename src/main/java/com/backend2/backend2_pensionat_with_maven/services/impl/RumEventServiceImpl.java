package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.dtos.RumEventDto;
import com.backend2.backend2_pensionat_with_maven.models.RumEvent;
import com.backend2.backend2_pensionat_with_maven.repos.RumEventRepo;
import com.backend2.backend2_pensionat_with_maven.services.RumEventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class RumEventServiceImpl implements RumEventService {

    private final RumEventRepo rumEventRepo;

    @Override
    public String sparaRumEvent(String message) throws JsonProcessingException {

        System.out.println(" I spara RumEvent.....");

     /*   RumEvent.RumEventType event = new ObjectMapper()
                .readerFor(RumEvent.RumEventType.class)
                .readValue(message);*/

        String output = "";
        RumEventDto.RumEventTypeDto event = new ObjectMapper()
                .readerFor(RumEventDto.RumEventTypeDto.class)
                .readValue(message);

        if(event instanceof RumEventDto.Opened){
            output = "Dörr öppnad";
        }
        else if(event instanceof RumEventDto.Closed){
            output = "Dörr stängd";
        }
        else if(event instanceof RumEventDto.StartCleaning startCleaning){
            output = "Städning påbörjad av: " + startCleaning.CleaningByUser;
        }
        else if(event instanceof RumEventDto.FinishCleaning finishCleaning){
            output = "Städning avslutad av: " + finishCleaning.CleaningByUser;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd - HH:mm:ss");
        System.out.println("Event: Rum: " +  event.RoomNo + " Händelse: " + output + " Tid: " + event.TimeStamp.toLocalDateTime().format(formatter));
        //rumEventRepo.save(event);
        return "Event saved";
    }
}
