package io.vscale.uniservice.security.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 04.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Component
@ResponseStatus(HttpStatus.FOUND)
public class MovedTemporarilyException extends RuntimeException{

   public MovedTemporarilyException(){
       super("Для доступа необходимо войти в систему");
   }

}
