package com.backend2.backend2_pensionat_with_maven.dtos;

import com.backend2.backend2_pensionat_with_maven.models.RumEvent;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RumEventDto {

    public RumEventTypeDto eventDto;

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            property = "type")
        /*    use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.PROPERTY,
            property = "type")*/
    @JsonSubTypes({
            @JsonSubTypes.Type(value = RumEvent.Opened.class, name = "RoomOpened"),
            @JsonSubTypes.Type(value = RumEvent.Closed.class, name = "RoomClosed"),
            @JsonSubTypes.Type(value = RumEvent.StartCleaning.class, name = "RoomCleaningStarted"),
            @JsonSubTypes.Type(value = RumEvent.FinishCleaning.class, name = "RoomCleaningFinished")
    })


    public static class RumEventTypeDto {

        private int RoomNo;
        private String TimeStamp;
        //public String type;
    }

    @JsonTypeName("RoomOpened")
    public static class Opened extends RumEvent.RumEventType {
        //public String name;
        //public String type;
    }

    @JsonTypeName("RoomClosed")
    public static class Closed extends RumEvent.RumEventType {
        //public String name;
    }

    @JsonTypeName("RoomCleaningStarted")
    public static class StartCleaning extends RumEvent.RumEventType {
        //public String name;
        public String CleaningByUser;
    }

    @JsonTypeName("RoomCleaningFinished")
    public static class FinishCleaning extends RumEvent.RumEventType {
        // public String name;
        public String CleaningByUser;
    }
}
