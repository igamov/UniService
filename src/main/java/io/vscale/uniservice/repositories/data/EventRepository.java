package io.vscale.uniservice.repositories.data;

import io.vscale.uniservice.domain.Event;
import io.vscale.uniservice.domain.EventTypeEvaluation;
import io.vscale.uniservice.dto.EventDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    @Query("SELECT new io.vscale.uniservice.dto.EventDTO(e.name, e.eventTypeName, e.eventDate, e.description) from Event e")
    List<EventDTO> findAllEventsAsDTO();

}
