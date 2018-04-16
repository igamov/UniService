package io.vscale.uniservice.services.interfaces.schedule;

import io.vscale.uniservice.domain.Schedule;

import java.util.List;

/**
 * 19.03.2018
 *
 * @author Dias Arkharov
 * @version 1.0
 */
public interface ScheduleService {

    List<Schedule> findAll();

    Schedule findById(Long id);

    void delete(Long id);

    void delete(Schedule schedule);

    void save(Schedule schedule);
}

