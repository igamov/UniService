package io.vscale.uniservice.controllers.rest.event;

import io.vscale.uniservice.domain.Event;
import io.vscale.uniservice.domain.User;
import io.vscale.uniservice.security.rest.exceptions.EntityNotFoundException;
import io.vscale.uniservice.services.interfaces.auth.AuthenticationService;
import io.vscale.uniservice.services.interfaces.events.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * 19.03.2018
 *
 * @author Aynur Aymurzin
 * @version 1.0
 */
@RestController
@RequestMapping("/api_v1")
public class EventRestController {

    private EventService eventService;
    private final AuthenticationService authenticationService;

    @Autowired
    public EventRestController(EventService eventService,
                               @Qualifier("restAuthenticationService") AuthenticationService authenticationService){
        this.eventService = eventService;
        this.authenticationService = authenticationService;
    }

    // ----------- FindAll Available Events ------------- //
    @GetMapping(value = "/event/")
    public ResponseEntity<List<Event>> listAllEvents(Authentication authentication) throws EntityNotFoundException{

        User user = this.authenticationService.getUserByAuthentication(authentication);
        List<Event> events = this.eventService.getEventsByUser(user);

        if (events.isEmpty()) {
            throw new EntityNotFoundException(Event.class, authentication.toString());
        }
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // ----------------- Get Event ---------------------- //

    @GetMapping(value = "/event/{id}")
    public ResponseEntity<?> getEvent(@PathVariable("id") Long id,
                                      Authentication authentication) throws EntityNotFoundException{
        Event event = eventService.findOne(id);
        if (event == null){
            throw new EntityNotFoundException(Event.class, authentication.toString());
        }
        return new ResponseEntity<>(event , HttpStatus.OK);
    }

    // ----------------- Create Event ------------------- //
    @PostMapping(value = "/event/")
    public ResponseEntity<?> addEvent(@RequestBody Event event, UriComponentsBuilder UriBuilder, Authentication authentication){
        eventService.save(event);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(UriBuilder.path("/api_v1/event/{id}").buildAndExpand(event.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
}
