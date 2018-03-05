package io.vscale.uniservice.security.rest.errors;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import lombok.NoArgsConstructor;

/**
 * 04.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString
public class APIValidationError extends APISubError{

    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public APIValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }

}
