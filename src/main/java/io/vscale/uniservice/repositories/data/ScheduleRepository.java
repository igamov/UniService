package io.vscale.uniservice.repositories.data;

import io.vscale.uniservice.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 12.03.2018
 *
 * @author Dias Arkharov
 * @version 1.0
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Long>{
}
