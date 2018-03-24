package io.vscale.uniservice.controllers.general.event;

import io.vscale.uniservice.domain.Event;
import io.vscale.uniservice.services.interfaces.events.EventService;
import io.vscale.uniservice.utils.PageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/events")
    public String allEvents(Model model,@PageableDefault(value = 4) Pageable pageable){

        PageWrapper<Event> pageWrapper =
                new PageWrapper<>(eventService.findAll(pageable), "/events");

        model.addAttribute("page", pageWrapper);
        return "all_events";
    }

}
