package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.models.RumEvent;
import com.backend2.backend2_pensionat_with_maven.repos.RumEventTypeRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.nio.file.attribute.AclEntryType;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class RumEventServiceImplUnitTests {

    private final RumEventTypeRepo rumEventTypeRepo = mock(RumEventTypeRepo.class);
    @Spy
    private final RumEventServiceImpl sut = new RumEventServiceImpl(rumEventTypeRepo);

    private File file;
    private ObjectMapper mapper;
    private List<String> fakeMessages;
    List<RumEvent.RumEventType> rumEvents = new ArrayList<>();

    @BeforeEach
    void setUp() throws JsonProcessingException {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        file = new File("src/test/resources/eventTestData.json");
        fakeMessages = new ArrayList<>();
        fakeMessages.add("{\"type\":\"RoomOpened\",\"TimeStamp\":\"2024-05-26T04:02:58.829879275\",\"RoomNo\":\"4\"}");
        fakeMessages.add("{\"type\":\"RoomClosed\",\"TimeStamp\":\"2024-05-26T11:54:58.847417154\",\"RoomNo\":\"8\"}");
        fakeMessages.add("{\"type\":\"RoomOpened\",\"TimeStamp\":\"2024-05-26T04:02:58.829879275\",\"RoomNo\":\"5\"}");
        fakeMessages.add("{\"type\":\"RoomClosed\",\"TimeStamp\":\"2024-05-26T11:54:58.847417154\",\"RoomNo\":\"2\"}");
        fakeMessages.add("{\"type\":\"RoomCleaningStarted\",\"TimeStamp\":\"2024-05-26T20:02:59.023471262\",\"RoomNo\":\"5\",\"CleaningByUser\":\"Van Doyle\"}");
        fakeMessages.add("{\"type\":\"RoomCleaningFinished\",\"TimeStamp\":\"2024-05-26T05:52:58.90806558\",\"RoomNo\":\"2\",\"CleaningByUser\":\"Oscar Huels\"}");

        for(String message: fakeMessages){
            rumEvents.add(sut.createRumEventTypeObjectFromMessage(message));
        }
    }

    @Test
    void createRumEventTypeObjectFromMessageShouldMapCorrectly() throws JsonProcessingException {

        //Assert
        assertEquals(rumEvents.size(), 6);
        assert(rumEvents.get(0) instanceof RumEvent.Opened);
        assert(rumEvents.get(1) instanceof RumEvent.Closed);
        assert(rumEvents.get(4) instanceof RumEvent.StartCleaning);
        assert(rumEvents.get(5) instanceof RumEvent.FinishCleaning);
    }

    @Test
    void eventToStringCreatesCorrectStringFromRumEventTypeObject() throws JsonProcessingException {

        //Assert
        assertTrue(sut.eventToString(rumEvents.get(2)).contains("Dörr öppnad"));
        assertTrue(sut.eventToString(rumEvents.get(3)).contains("Dörr stängd"));
        assertTrue(sut.eventToString(rumEvents.get(4)).contains("Städning påbörjad av"));
        assertTrue(sut.eventToString(rumEvents.get(4)).contains("Van Doyle"));
        assertTrue(sut.eventToString(rumEvents.get(5)).contains("Städning avslutad av"));
        assertTrue(sut.eventToString(rumEvents.get(5)).contains("Oscar Huels"));
    }

    @Test
    void findEventsByRoomReturnsCorrectNumberOfRumEvents() throws JsonProcessingException {

        //Arrange
        when(rumEventTypeRepo.findAll()).thenReturn(rumEvents);
        //Act/Assert
        assertEquals(sut.findEventsByRoomNr(1).size(), 0);
        assertEquals(sut.findEventsByRoomNr(2).size(), 2);
        assertNotEquals(sut.findEventsByRoomNr(3).size(), 1);
        assertEquals(sut.findEventsByRoomNr(4).size(), 1);
        assertEquals(sut.findEventsByRoomNr(5).size(), 2);
        assertEquals(sut.findEventsByRoomNr(8).size(), 1);
    }
}