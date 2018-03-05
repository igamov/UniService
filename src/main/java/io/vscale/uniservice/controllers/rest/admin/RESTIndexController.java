package io.vscale.uniservice.controllers.rest.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 04.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@RestController
@RequestMapping("/api_v1")
public class RESTIndexController {

    @GetMapping("/check")
    public @ResponseBody String getMessage(){
        return "{\"test\" : \"hellO!\"}";
    }


}
