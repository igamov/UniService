package io.vscale.uniservice.dto.transfers;

import io.vscale.uniservice.domain.Event;
import io.vscale.uniservice.domain.EventTypeEvaluation;
import io.vscale.uniservice.dto.domain.EventTypeEvaluationDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 19.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public class EventTypeEvaluationTransfer {

    public List<EventTypeEvaluationDTO> getTransferEvaluations(List<EventTypeEvaluation> evaluations){

        List<EventTypeEvaluationDTO> resultList = new ArrayList<>();

        evaluations.forEach(eventTypeEvaluation -> {

            List<Event> events = eventTypeEvaluation.getEvents();

            events.forEach(event -> {

                resultList.add(EventTypeEvaluationDTO.builder()
                                                     .id(eventTypeEvaluation.getId())
                                                     .type(eventTypeEvaluation.getType())
                                                     .startValue(eventTypeEvaluation.getStartValue())
                                                     .endValue(eventTypeEvaluation.getEndValue())
                                                     .eventId(event.getId())
                                                     .build());

            });

        });

        return resultList;

    }

}
