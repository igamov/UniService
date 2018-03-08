package io.vscale.uniservice.models;

import io.vscale.uniservice.application.Application;
import io.vscale.uniservice.domain.Event;
import io.vscale.uniservice.domain.EventTypeEvaluation;
import io.vscale.uniservice.repositories.EventTypeEvaluationRepository;
import io.vscale.uniservice.services.interfaces.events.EventService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

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
public class EventWithEventTypeEvaluationIntegrationTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventTypeEvaluationRepository evaluationRepository;

    private Event event;
    private EventTypeEvaluation typeEvaluation;

    @Before
    public void setEventAndTypeEvaluation(){

        this.event = Event.builder()
                          .description("Test")
                          .eventTypeEvaluations(new ArrayList<>())
                          .build();

        this.typeEvaluation = EventTypeEvaluation.builder()
                                            .type("Meeting")
                                            .startValue(new Integer(0).byteValue())
                                            .endValue(new Integer(10).byteValue())
                                            .build();

    }

    @Test // создаем Event и EventType заливаем их в бд и находим Event по EventType
    public void findOneByTypeEvaluation() {
        this.event.getEventTypeEvaluations().add(this.typeEvaluation);
        this.eventService.save(this.event);
        assertEquals(this.event.getId(), this.eventService.findOneByEventTypeEvaluations(this.typeEvaluation).getId());
    }

    @Test // создаем Event и EventType заливаем их в бд, удаляем Event проверяем удалилась ли она из бд
    // и проверяем не удалился ли EventType
    public void deleteAndCheckTypeEvaluationExisting() {
        this.typeEvaluation = EventTypeEvaluation.builder()
                                            .type("JAVA DAY")
                                            .startValue(new Integer(0).byteValue())
                                            .endValue(new Integer(10).byteValue())
                                            .build();

        this.event.getEventTypeEvaluations().add(this.typeEvaluation);
        this.eventService.save(this.event);

        this.eventService.delete(this.event);
        assertNull(this.eventService.findOne(this.event.getId()));
        assertNotNull(this.evaluationRepository.findByType("JAVA DAY"));
    }

    @Test // // создаем Event и EventType заливаем их в бд, ишем EventType по Event
    public void findEventTypeEvaluationByEvent(){
        this.event.getEventTypeEvaluations().add(this.typeEvaluation);
        this.eventService.save(this.event);

        EventTypeEvaluation evaluation = this.evaluationRepository.findByEventsContaining(this.event);
        assertEquals(evaluation.getId(), this.typeEvaluation.getId());
        assertNotNull(evaluation);
    }
}
