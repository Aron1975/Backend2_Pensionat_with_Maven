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
    private int rumsNummer;
    private Timestamp timestamp;

    @ManyToOne
    public RumEventType eventType;

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.PROPERTY,
            property = "eventType")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = Opened.class, name = "opened"),
            @JsonSubTypes.Type(value = Closed.class, name = "closed"),
            @JsonSubTypes.Type(value = StartCleaning.class, name = "startCleaning"),
            @JsonSubTypes.Type(value = FinishCleaning.class, name = "finishCleaning")
    })

    @Entity
    public static class RumEventType {
        @Id
        @GeneratedValue
        private long id;
        public String name;
    }

    @JsonTypeName("opened")
    public static class Opened extends RumEventType {
        public String name;
    }

    @JsonTypeName("closed")
    public static class Closed extends RumEventType {
        public String name;
    }

    @JsonTypeName("startCleaning")
    public static class StartCleaning extends RumEventType {
        public String name;
        public String cleaner;
    }

    @JsonTypeName("finishCleaning")
    public static class FinishCleaning extends RumEventType {
        public String name;
        public String cleaner;
    }

}



