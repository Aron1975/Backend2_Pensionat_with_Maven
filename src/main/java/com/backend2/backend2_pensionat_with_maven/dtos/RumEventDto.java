package com.backend2.backend2_pensionat_with_maven.dtos;

import com.backend2.backend2_pensionat_with_maven.models.RumEvent;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.*;
import com.rabbitmq.tools.json.JSONUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.*;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;

/*@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder*/
@JsonIgnoreProperties(ignoreUnknown = true)
public class RumEventDto {

    public RumEventTypeDto eventDto;
   // public String type;
   // public String TimeStamp;


    @JsonTypeInfo(//use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property="type")
            use = JsonTypeInfo.Id.NAME,
            include = PROPERTY,
            property = "type")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = Opened.class, name = "RoomOpened"),
            @JsonSubTypes.Type(value = Closed.class, name = "RoomClosed"),
            @JsonSubTypes.Type(value = StartCleaning.class, name = "RoomCleaningStarted"),
            @JsonSubTypes.Type(value = FinishCleaning.class, name = "RoomCleaningFinished")
    })


    public static class RumEventTypeDto{

        public int RoomNo;
        public String TimeStamp;
        //private String type;
        //public String type;
    }


    @JsonTypeName("RoomOpened")
    public static class Opened extends RumEventDto.RumEventTypeDto {
        //public String name;
        //public String type;
    }


    @JsonTypeName("RoomClosed")
    public static class Closed extends RumEventDto.RumEventTypeDto {
        //public String type;
    }


    @JsonTypeName("RoomCleaningStarted")
    public static class StartCleaning extends RumEventDto.RumEventTypeDto {
        //public String name;
        public String CleaningByUser;
    }


    @JsonTypeName("RoomCleaningFinished")
    public static class FinishCleaning extends RumEventDto.RumEventTypeDto {
        // public String name;
        public String CleaningByUser;
    }
}
