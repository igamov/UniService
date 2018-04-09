package io.vscale.uniservice.tests.models;

import io.vscale.uniservice.application.Application;
import io.vscale.uniservice.domain.Organization;
import io.vscale.uniservice.repositories.data.OrganizationRepository;
import io.vscale.uniservice.repositories.data.StudentRepository;
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
public class StudentTest {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    @Transactional
    public void saveOrganization() {
        Organization organization = Organization.builder()
                                                .title("MusicClub")
                                                .build();

        this.organizationRepository.save(organization);
        assertNotNull(this.organizationRepository.getOne(organization.getId()));
    }

    @Test
    @Transactional
    public void deleteOrganization(){
        Organization organization = Organization.builder()
                                                .title("SportClub")
                                                .build();

        this.organizationRepository.save(organization);
        this.organizationRepository.delete(organization.getId());
        Organization foundOrganization = this.organizationRepository.findOne(organization.getId());
        assertNull(foundOrganization);
    }


    /*@Test
    @Transactional
    public void saveOrganizationWithStudents(){
        Organization organization = Organization.builder()
                .title("neMusicClub")
                .students(new HashSet<>())
                .build();

        Student student1 = Student.builder()
                .gender("M")
                .build();

        Student student2 = Student.builder()
                .gender("M")
                .build();

        organization.getStudents().add(student1);
        organization.getStudents().add(student2);
        organizationRepository.save(organization);

        assertTrue(organization.getStudents().contains(student1));
        assertTrue(organization.getStudents().contains(student1));
    }*/

}
