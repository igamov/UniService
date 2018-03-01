package io.vscale.uniservice.repositories;

import io.vscale.uniservice.domain.Event;
import io.vscale.uniservice.domain.EventTypeEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 01.03.2018
 *
 * @author Aynur Aymurzin
 * @version 1.0
 */
public interface EventRepository extends JpaRepository<Event, Long> {

    Event findOneByEventTypeEvaluations(List<EventTypeEvaluation> typeEvaluation);
    Event findOneByDescription(String description);

}
