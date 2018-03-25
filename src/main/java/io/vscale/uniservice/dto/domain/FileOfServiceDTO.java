package io.vscale.uniservice.dto.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import lombok.NoArgsConstructor;

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
public class FileOfServiceDTO {

    private Long id;
    private String originalName;
    private String url;

}
