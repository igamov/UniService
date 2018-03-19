package io.vscale.uniservice.dto.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 19.03.2018
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

    private Long id;
    private String description;
    private Timestamp timestamp;
    private Long evaluationId;
    private Long studentId;
    private Long fileId;
    private Long managerId;

}
