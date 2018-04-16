package io.vscale.uniservice.tests.models;

import io.vscale.uniservice.application.Application;
import io.vscale.uniservice.domain.FileOfService;
import io.vscale.uniservice.domain.Organization;
import io.vscale.uniservice.repositories.data.FileOfServiceRepository;
import io.vscale.uniservice.repositories.data.OrganizationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

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
public class FileOfServiceTest {

    @Autowired
    private FileOfServiceRepository fileOfServiceRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    @Transactional
    public void saveFileOfService(){
        FileOfService file = FileOfService.builder()
                                          .originalName("TestFile")
                                          .encodedName("EncodedTestFile")
                                          .type("text/plain")
                                          .url("//ggwp")
                                          .build();

        this.fileOfServiceRepository.save(file);
        assertNotNull(this.fileOfServiceRepository.getOne(file.getId()));
    }

    /*@Test
    @Transactional
    public void delteFileOfService(){
        FileOfService file = FileOfService.builder()
                .originalName("TestFile1")
                .encodedName("EncodedTestFile1")
                .type("text/plain")
                .url("//ggwp2")
                .build();
        fileOfServiceRepository.save(file);
        fileOfServiceRepository.delete(file);
        FileOfService foundFile = fileOfServiceRepository.getOne(file.getId());
        assertNull(foundFile);
    }*/

    @Test
    @Transactional
    public void saveOrganizationWithFileOfService(){
        FileOfService file1 = FileOfService.builder()
                                           .originalName("TestFile2")
                                           .encodedName("EncodedTestFile2")
                                           .type("text/plain")
                                           .url("//ggwp3")
                                           .build();

        FileOfService file2 = FileOfService.builder()
                                           .originalName("TestFile3")
                                           .encodedName("EncodedTestFile3")
                                           .type("text/plain")
                                           .url("//ggwp4")
                                           .build();

        Organization organization = Organization.builder()
                                                .title("newOrganization")
                                                .organizationFiles(new HashSet<>())
                                                .build();

        this.organizationRepository.save(organization);

        organization.getOrganizationFiles().add(file1);
        organization.getOrganizationFiles().add(file2);

        Organization foundOrganization = this.organizationRepository.findOne(organization.getId());

        assertNotNull(foundOrganization.getOrganizationFiles());
        assertTrue(foundOrganization.getOrganizationFiles().contains(file1));
        assertTrue(foundOrganization.getOrganizationFiles().contains(file2));
    }

}
