package io.vscale.uniservice.dto.transfers;

import io.vscale.uniservice.domain.*;
import io.vscale.uniservice.dto.domain.EventDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 19.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public class EventTransfer {

    public List<EventDTO> getTransferEventsWithStudents(List<Event> events){

        List<EventDTO> resultList = new ArrayList<>();

        events.forEach(event -> {

            Set<Student> students = event.getStudents();

            students.forEach(student ->
                    resultList.add(EventDTO.builder()
                                           .id(event.getId())
                                           .description(event.getDescription())
                                           .timestamp(event.getTimestamp())
                                           .studentId(student.getId())
                                           .build()));

        });

        return resultList;

    }

}
