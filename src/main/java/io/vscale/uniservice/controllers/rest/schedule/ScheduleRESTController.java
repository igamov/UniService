package io.vscale.uniservice.controllers.rest.schedule;

import io.vscale.uniservice.domain.Schedule;
import io.vscale.uniservice.services.interfaces.schedule.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 19.03.2018
 *
 * @author Dias Arkharov
 * @version 1.0
 */
@RestController
@RequestMapping("/api_v1/schedule")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ScheduleRESTController {

    private ScheduleService scheduleService;

    @GetMapping("/show/schedules")
    @ResponseBody
    public List<Schedule> getSchedules(){
        return scheduleService.findAll();
    }

    @GetMapping("/show/schedules/{id}")
    @ResponseBody
    public Schedule getScheduleById(@PathVariable("id") Long id){
        return scheduleService.findById(id);
    }

    @DeleteMapping("/schedule/delete/{id}")
    public void deleteSchedule(@PathVariable("id") Long id){
        scheduleService.delete(id);
    }

    @PostMapping("/schedule/save")
    public void saveSchedule(@RequestBody Schedule schedule){
        scheduleService.save(schedule);
    }

}
