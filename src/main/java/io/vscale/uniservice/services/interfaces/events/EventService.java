package io.vscale.uniservice.services.interfaces.events;

import io.vscale.uniservice.domain.Event;
import io.vscale.uniservice.domain.EventTypeEvaluation;
import io.vscale.uniservice.forms.general.NewEventForm;

import java.util.List;

/**
 * 01.03.2018
 *
 * @author Aynur Aymurzin
 * @version 1.0
 */
public interface EventService {
    List<Event> findByTypeEvaluation(EventTypeEvaluation typeEvaluation);
    void delete(Event event);
    Event save(Event event);
    Event findOneByEventTypeEvaluations(EventTypeEvaluation evaluation);
    Event findOne(Long id);
    List<Event> findAll();
    boolean getNewEvents();
    void addEventWithChecking(NewEventForm newEventForm);
}
