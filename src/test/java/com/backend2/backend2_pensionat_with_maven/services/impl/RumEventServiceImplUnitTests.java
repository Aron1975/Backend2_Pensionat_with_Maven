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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class RumEventServiceImplUnitTests {

    private final RumEventTypeRepo rumEventTypeRepo = mock(RumEventTypeRepo.class);
    @Spy
    private final RumEventServiceImpl sut = new RumEventServiceImpl(rumEventTypeRepo);

    private File file;
    private ObjectMapper mapper;
    private List<String> fakeMessages;

    @BeforeEach
    void setUp() {
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
    }

    @Test
    void findEventsByRoomNr() { //Repo
    }

    @Test
    void getEventListByRoomNr() {
    }

    @Test
    void createRumEventTypeObjectFromMessageShouldMapCorrectly() throws JsonProcessingException {
        //Arrrange
        List<RumEvent.RumEventType> rumEvents = new ArrayList<>();

        //Act
        for(String message: fakeMessages){
            rumEvents.add(sut.createRumEventTypeObjectFromMessage(message));
        }

        //Assert
        assertEquals(rumEvents.size(), 6);
        assert(rumEvents.get(0) instanceof RumEvent.Opened);
        assert(rumEvents.get(1) instanceof RumEvent.Closed);
        assert(rumEvents.get(4) instanceof RumEvent.StartCleaning);
        assert(rumEvents.get(5) instanceof RumEvent.FinishCleaning);
    }

    @Test
    void sparaRumEvent() {

    }

    @Test
    void eventToString() {
    }
}