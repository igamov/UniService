package io.vscale.uniservice.tests.models;

import io.vscale.uniservice.application.Application;
import io.vscale.uniservice.domain.Organization;
import io.vscale.uniservice.domain.Schedule;
import io.vscale.uniservice.repositories.data.OrganizationRepository;
import io.vscale.uniservice.repositories.data.ScheduleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * 12.03.2018
 *
 * @author Dias Arkharov
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class ScheduleTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    @Transactional
    public void saveSchedule(){
        Schedule schedule = Schedule.builder()
                                    .description("test")
                                    .build();

        this.scheduleRepository.save(schedule);
        Schedule foundSchedule = this.scheduleRepository.findOne(schedule.getId());
        assertNotNull(foundSchedule);
    }

    @Test
    @Transactional
    public void deleteSchedule(){
        Schedule schedule = Schedule.builder()
                                    .description("test2")
                                    .build();

        this.scheduleRepository.save(schedule);
        this.scheduleRepository.delete(schedule.getId());
        Schedule foundSchedule = this.scheduleRepository.findOne(schedule.getId());
        assertNull(foundSchedule);
    }

    @Test
    @Transactional
    public void saveScheduleToOrganization(){
        Schedule schedule = Schedule.builder()
                                    .description("test3")
                                    .build();

        Organization organization = Organization.builder()
                                                .title("Chess")
                                                .schedule(schedule)
                                                .build();

        this.organizationRepository.save(organization);
        Organization foundOrganization = this.organizationRepository.findOne(organization.getId());
        assertNotNull(foundOrganization);
        assertEquals(foundOrganization.getSchedule().getId(), schedule.getId());
    }

    @Test
    @Transactional
    public void deleteOrganizationWithSchedule(){

        Schedule schedule = Schedule.builder()
                                    .description("test4")
                                    .build();

        Organization organization = Organization.builder()
                                                .title("Football")
                                                .schedule(schedule)
                                                .build();

        schedule.setOrganization(organization);
        this.organizationRepository.save(organization);
        this.organizationRepository.delete(organization);
        Organization foundOrganization = this.organizationRepository.findOne(organization.getId());
        assertNull(foundOrganization);
        //???assertNull(scheduleRepository.findOne(schedule.getId()));
    }

}
