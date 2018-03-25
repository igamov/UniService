package io.vscale.uniservice.repositories.data;

import io.vscale.uniservice.domain.Event;
import io.vscale.uniservice.domain.EventTypeEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 01.03.2018
 *
 * @author Aynur Aymurzin
 * @version 1.0
 */
public interface EventTypeEvaluationRepository extends JpaRepository<EventTypeEvaluation, Long> {
    EventTypeEvaluation findByType(String string);
    EventTypeEvaluation findByEventsContaining(Event event);
}
