package com.backend2.backend2_pensionat_with_maven.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RumEventDto {

    public RumEventTypeDto eventDto;

    @JsonTypeInfo(
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
        public Timestamp TimeStamp;
    }

    @JsonTypeName("RoomOpened")
    public static class Opened extends RumEventDto.RumEventTypeDto {
    }

    @JsonTypeName("RoomClosed")
    public static class Closed extends RumEventDto.RumEventTypeDto {
    }

    @JsonTypeName("RoomCleaningStarted")
    public static class StartCleaning extends RumEventDto.RumEventTypeDto {
        public String CleaningByUser;
    }

    @JsonTypeName("RoomCleaningFinished")
    public static class FinishCleaning extends RumEventDto.RumEventTypeDto {
        public String CleaningByUser;
    }
}
