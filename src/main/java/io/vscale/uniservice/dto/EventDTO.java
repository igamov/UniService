package io.vscale.uniservice.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 07.04.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class EventDTO {

    private String title;
    private String eventType;
    private Timestamp eventDate;
    private String description;

    public EventDTO(String title, String eventType, Date eventDate, String description){
        this.title = title;
        this.eventType = eventType;
        this.eventDate = (Timestamp) eventDate;
        this.description = description;
    }

}
