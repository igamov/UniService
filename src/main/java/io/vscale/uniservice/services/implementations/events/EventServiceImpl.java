package io.vscale.uniservice.services.implementations.events;

import io.vscale.uniservice.domain.Event;
import io.vscale.uniservice.domain.EventTypeEvaluation;
import io.vscale.uniservice.repositories.EventRepository;
import io.vscale.uniservice.services.interfaces.events.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 01.03.2018
 *
 * @author Aynur Aymurzin
 * @version 1.0
 */
@Service
public class EventServiceImpl implements EventService {

    private EventRepository repository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository){
        this.repository = eventRepository;
    }

    @Override
    public List<Event> findByTypeEvaluation(EventTypeEvaluation typeEvaluation) {

        List<Event> currentEvents = this.repository.findAll();

        return currentEvents.stream()
                            .filter(event -> event.getEventTypeEvaluations().equals(typeEvaluation))
                            .collect(Collectors.toList());

    }

    @Override
    public void delete(Event event) {
        repository.delete(event);
    }

    @Override
    public void save(Event event) {
        repository.save(event);
    }

    @Override
    public Event findOneByEventTypeEvaluations(EventTypeEvaluation evaluation) {
        return repository.findOneByEventTypeEvaluations(Collections.singletonList(evaluation));
    }

    @Override
    public Event findOne(Long id) {
        return repository.findOne(id);
    }
}
