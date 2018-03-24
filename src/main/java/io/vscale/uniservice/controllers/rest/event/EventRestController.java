package io.vscale.uniservice.controllers.rest.event;

import io.vscale.uniservice.domain.Event;
import io.vscale.uniservice.services.interfaces.events.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api_v1")
public class EventRestController {

    private EventService eventService;

    @Autowired
    public EventRestController(EventService eventService){
        this.eventService = eventService;
    }

    // ----------- FindAll Available Events ------------- //
    @GetMapping(value = "/event/")
    public ResponseEntity<List<Event>> listAllEvents(Authentication authentication) {
        if (authentication == null){
            return new ResponseEntity("Вы не авторизованы", HttpStatus.UNAUTHORIZED);
        }
        List<Event> events = eventService.findAll();
        if (events.isEmpty()) {
            return new ResponseEntity("empty", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // ----------------- Get Event ---------------------- //

    @GetMapping(value = "/event/{id}")
    public ResponseEntity<?> getEvent(@PathVariable("id") Long id, Authentication authentication) {
        if (authentication == null){
            return new ResponseEntity("Вы не авторизованы", HttpStatus.UNAUTHORIZED);
        }
        Event event = eventService.findOne(id);
        if (event == null){
            return new ResponseEntity("error", HttpStatus.NOT_FOUND);
        }
          return new ResponseEntity<>(event , HttpStatus.OK);
    }

    // ----------------- Create Event ------------------- //
    @PostMapping(value = "/event/")
    public ResponseEntity<?> addEvent(@RequestBody Event event, UriComponentsBuilder UriBuilder, Authentication authentication){
        if (authentication == null){
            return new ResponseEntity("Вы не авторизованы", HttpStatus.UNAUTHORIZED);
        }
        eventService.save(event);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(UriBuilder.path("/api_v1/event/{id}").buildAndExpand(event.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
}
