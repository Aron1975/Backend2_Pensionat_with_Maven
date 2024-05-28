package com.backend2.backend2_pensionat_with_maven.services;

import com.backend2.backend2_pensionat_with_maven.dtos.RumEventDto;
import com.backend2.backend2_pensionat_with_maven.models.RumEvent;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface RumEventService {

    public String sparaRumEvent(String message) throws JsonProcessingException;
    public List<RumEvent.RumEventType> findEventsByRoomNr(int roomNr) throws JsonProcessingException;
    public List<String> getEventListByRoomNr(int roomNr) throws JsonProcessingException ;
    public RumEvent.RumEventType createRumEventTypeObjectFromMessage(String message) throws JsonProcessingException;
}
