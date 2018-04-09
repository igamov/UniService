package io.vscale.uniservice.tests.models;

import io.vscale.uniservice.application.Application;
import io.vscale.uniservice.domain.Event;
import io.vscale.uniservice.domain.FileOfService;
import io.vscale.uniservice.repositories.data.EventRepository;
import io.vscale.uniservice.repositories.data.FileOfServiceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * 05.03.2018
 *
 * @author Aynur Aymurzin
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class EventWithFilesIntegrationTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private FileOfServiceRepository fileRepository;

    @Test // Сохраняем Event с FileOfService и проверяем есть ли File в бд
    public void saveEventWithFile(){
        Event event = Event.builder()
                           .description("Test")
                           .files(new HashSet<>())
                           .build();

        FileOfService fileOfService = FileOfService.builder()
                                                   .originalName("TestFile")
                                                   .encodedName("EncodedTestFile")
                                                   .type("text/plain")
                                                   .url("///hello")
                                                   .build();

        event.getFiles().add(fileOfService);
        this.eventRepository.save(event);
        FileOfService file = this.fileRepository.findOneById(fileOfService.getId());
        assertEquals(file.getId(), fileOfService.getId());
    }

    @Test // сохраняем Event c FileOfService и находим File по Event
    public void findFileByEvent(){
        Event event = Event.builder()
                           .description("Test")
                           .files(new HashSet<>())
                           .build();

        FileOfService fileOfService = FileOfService.builder()
                                                   .originalName("TestFile")
                                                   .encodedName("EncodedTestFile")
                                                   .type("text/plain")
                                                   .url("///hello")
                                                   .build();

        event.getFiles().add(fileOfService);
        this.eventRepository.save(event);
        FileOfService foundFile = this.fileRepository.findByEventsContaining(event);
        assertEquals(foundFile.getId(), fileOfService.getId());
    }

    @Test // сохраняем Event c FileOfService и находим File по Event
    public void checkFileExistingAfterDeleteEvent(){
        Event event = Event.builder()
                           .description("Test")
                           .files(new HashSet<>())
                           .build();

        FileOfService fileOfService = FileOfService.builder()
                                                   .originalName("TestFile")
                                                   .encodedName("EncodedTestFile")
                                                   .type("text/plain")
                                                   .url("///hello")
                                                   .build();

        event.getFiles().add(fileOfService);
        this.eventRepository.save(event);
        this.eventRepository.delete(event);
        FileOfService foundFile = this.fileRepository.findByEventsContaining(event);
        assertNull(foundFile);
        FileOfService foundFile2 = this.fileRepository.findOne(fileOfService.getId());
        assertNotNull(foundFile2);
    }
}
