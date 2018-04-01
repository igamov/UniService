package io.vscale.uniservice.controllers.general.admin;

import io.vscale.uniservice.domain.Event;
import io.vscale.uniservice.services.interfaces.events.EventService;
import io.vscale.uniservice.utils.PageWrapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 26.03.2018
 *
 * @author Aynur Aymurzin
 * @version 1.0
 */
@Controller
@RequestMapping("/admin")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventController {

    private EventService eventService;

    @GetMapping("/events")
    public ModelAndView showEvents(@PageableDefault(value = 4) Pageable pageable){

        PageWrapper<Event> pageWrapper =
                new PageWrapper<>(this.eventService.findAll(pageable), "/admin/events");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/admin-events");
        modelAndView.addObject("pageWrapper", pageWrapper);

        return modelAndView;

    }

}
