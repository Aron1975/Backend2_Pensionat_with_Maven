package com.backend2.backend2_pensionat_with_maven.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RumEvent {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    public RumEventType eventType;

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

    @Entity
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RumEventType {
        @Id
        @GeneratedValue
        private long id;
        public int RoomNo;
        public Timestamp TimeStamp;
    }

    @Entity
    @JsonTypeName("RoomOpened")
    public static class Opened extends RumEventType {

        @Id
        @GeneratedValue
        private long id;
    }

    @Entity
    @JsonTypeName("RoomClosed")
    public static class Closed extends RumEventType {

        @Id
        @GeneratedValue
        private long id;
    }

    @Entity
    @JsonTypeName("RoomCleaningStarted")
    public static class StartCleaning extends RumEventType {

        @Id
        @GeneratedValue
        private long id;
        public String CleaningByUser;
    }

    @Entity
    @JsonTypeName("RoomCleaningFinished")
    public static class FinishCleaning extends RumEventType {

        @Id
        @GeneratedValue
        private long id;
        public String CleaningByUser;
    }

}



