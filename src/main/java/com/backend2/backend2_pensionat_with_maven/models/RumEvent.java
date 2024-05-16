package com.backend2.backend2_pensionat_with_maven.models;

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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RumEvent {

    @Id
    @GeneratedValue
    private long id;
    //private int RoomNo;
    //private Timestamp timestamp;

    @ManyToOne
    public RumEventType type;

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.PROPERTY,
            property = "type")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = Opened.class, name = "RoomOpened"),
            @JsonSubTypes.Type(value = Closed.class, name = "RoomClosed"),
            @JsonSubTypes.Type(value = StartCleaning.class, name = "RoomCleaningStarted"),
            @JsonSubTypes.Type(value = FinishCleaning.class, name = "RoomCleaningFinished")
    })

    @Entity
    public static class RumEventType {
        @Id
        @GeneratedValue
        private long id;
        private int RoomNo;
        private Timestamp timestamp;
        //public String type;
    }

    @Entity
    @JsonTypeName("RoomOpened")
    public static class Opened extends RumEventType {
        public String name;
        //public String type;
    }

    @Entity
    @JsonTypeName("RoomClosed")
    public static class Closed extends RumEventType {
        public String name;
    }

    @Entity
    @JsonTypeName("RoomCleaningStarted")
    public static class StartCleaning extends RumEventType {
        //public String name;
        public String CleaningByUser;
    }

    @Entity
    @JsonTypeName("RoomCleaningFinished")
    public static class FinishCleaning extends RumEventType {
       // public String name;
        public String CleaningByUser;
    }

}



