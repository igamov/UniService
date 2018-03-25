package io.vscale.uniservice.controllers.rest.event;

import io.vscale.uniservice.domain.Event;
import io.vscale.uniservice.services.interfaces.events.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    public EventRestController(EventService eventService){
        this.eventService = eventService;
    }

    // ----------- FindAll Available Events ------------- //
    @GetMapping(value = "/event/")
    public ResponseEntity<List<Event>> listAllEvents() {
        List<Event> events = eventService.findAll();
        if (events.isEmpty()) {
            return new ResponseEntity("empty", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // ----------------- Get Event ---------------------- //

    @GetMapping(value = "/event/{id}")
    public ResponseEntity<?> getEvent(@PathVariable("id") Long id) {
        Event event = eventService.findOne(id);
        if (event == null){
            return new ResponseEntity("error", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(event , HttpStatus.OK);
    }

    // ----------------- Create Event ------------------- //
    @PostMapping(value = "/event/")
    public ResponseEntity<?> addEvent(@RequestBody Event event, UriComponentsBuilder UriBuilder){
        eventService.save(event);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(UriBuilder.path("/api_v1/event/{id}").buildAndExpand(event.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
}
