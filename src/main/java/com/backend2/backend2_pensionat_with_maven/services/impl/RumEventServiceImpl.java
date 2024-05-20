package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.dtos.RumEventDto;
import com.backend2.backend2_pensionat_with_maven.models.RumEvent;
import com.backend2.backend2_pensionat_with_maven.repos.RumEventRepo;
import com.backend2.backend2_pensionat_with_maven.repos.RumEventTypeRepo;
import com.backend2.backend2_pensionat_with_maven.services.RumEventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RumEventServiceImpl implements RumEventService {

    private final RumEventRepo rumEventRepo;
    private final RumEventTypeRepo rumEventTypeRepo;

    @Override
    public List<RumEvent.RumEventType> findEventsByRoomNr(int roomNr) throws JsonProcessingException {
       // List<RumEvent.RumEventType> rumEventList = rumEventTypeRepo.findByRoomNo();
        return rumEventTypeRepo.findAll().stream().filter(event -> event.RoomNo==roomNr).toList();
    }

    @Override
    public List<String> getEventListByRoomNr(int roomNr) throws JsonProcessingException {

        List<RumEvent.RumEventType> rumEventList = findEventsByRoomNr(roomNr);
        List<String> eventStringList = new ArrayList<>();
        for (RumEvent.RumEventType event : rumEventList) {
            eventStringList.add(eventToString(event));
        }
        return eventStringList;
    }

    @Override
    public String sparaRumEvent(String message) throws JsonProcessingException {

        String output = "";
        //RumEvent rumEvent = new RumEvent();

        RumEvent.RumEventType event = new ObjectMapper()
                .readerFor(RumEvent.RumEventType.class)
                .readValue(message);

        if(event instanceof RumEvent.Opened){
            output = "Dörr öppnad";
        }
        else if(event instanceof RumEvent.Closed){
            output = "Dörr stängd";
        }
        else if(event instanceof RumEvent.StartCleaning startCleaning){
            output = "Städning påbörjad av: " + startCleaning.CleaningByUser;
        }
        else if(event instanceof RumEvent.FinishCleaning finishCleaning){
            output = "Städning avslutad av: " + finishCleaning.CleaningByUser;
        }

       // RumEvent rumEvent = new RumEvent(event);

        rumEventTypeRepo.save(event);

 /*       RumEventDto.RumEventTypeDto event = new ObjectMapper()
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
        }*/
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd - HH:mm:ss");
        System.out.println("Event: Rum: " +  event.RoomNo + " Händelse: " + output + " Tid: " + event.TimeStamp.toLocalDateTime().format(formatter));
        //rumEventRepo.save(event);
        return "Event saved";
    }

    public String eventToString(RumEvent.RumEventType event) {

        String eventString = "";
        if(event instanceof RumEvent.Opened){
            eventString = "Dörr öppnad";
        }
        else if(event instanceof RumEvent.Closed){
            eventString = "Dörr stängd";
        }
        else if(event instanceof RumEvent.StartCleaning startCleaning){
            eventString = "Städning påbörjad av " + startCleaning.CleaningByUser;
        }
        else if(event instanceof RumEvent.FinishCleaning finishCleaning){
            eventString = "Städning avslutad av " + finishCleaning.CleaningByUser;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        eventString = eventString + " " + event.TimeStamp.toLocalDateTime().format(formatter);
        return eventString;
    }
}
