package io.vscale.uniservice.tests.models;

import io.vscale.uniservice.application.Application;
import io.vscale.uniservice.domain.Confirmation;
import io.vscale.uniservice.domain.FileOfService;
import io.vscale.uniservice.repositories.data.ConfirmationRepository;
import io.vscale.uniservice.repositories.data.FileOfServiceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * 05.03.2018
 *
 * @author Aynur Aymurzin
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class ConfirmationWithFilesIntegrationTest {

    @Autowired
    private FileOfServiceRepository fileRepository;

    @Autowired
    private ConfirmationRepository confirmationRepository;

    @Test // создаем Confirmation и FileOfService заливаем их в бд и находим Confirmation по File
    @Transactional
    public void saveConfirmationWithFile() {
        FileOfService file = FileOfService.builder()
                                          .originalName("TestFile")
                                          .encodedName("EncodedTestFile")
                                          .type("text/plain")
                                          .url("//hello")
                                          .confirmations(new ArrayList<>())
                                          .build();

        Confirmation confirmation = Confirmation.builder()
                                                .description("confirmation description")
                                                .fileOfService(file)
                                                .build();
        file.getConfirmations().add(confirmation);
        this.fileRepository.save(file);

        Confirmation foundConfirmation = this.confirmationRepository.findOneByFileOfService(file);
        assertEquals(confirmation.getId(), foundConfirmation.getId());
        assertEquals(foundConfirmation.getFileOfService().getId(), file.getId());

    }

    @Test
    @Transactional
    public void saveFileWithSomeConfirmations(){
        FileOfService file = FileOfService.builder()
                                          .originalName("1")
                                          .encodedName("EncodedTestFile")
                                          .type("text/plain")
                                          .confirmations(new ArrayList<>())
                                          .url("1")
                                          .build();

        Confirmation confirmation1 = Confirmation.builder()
                                                 .description("first")
                                                 .fileOfService(file)
                                                 .build();

        Confirmation confirmation2 = Confirmation.builder()
                                                 .description("second")
                                                 .fileOfService(file)
                                                 .build();
        file.getConfirmations().add(confirmation1);
        file.getConfirmations().add(confirmation2);
        this.fileRepository.save(file);

        FileOfService foundFile = this.fileRepository.findOne(file.getId());
        Confirmation foundConfirmation = this.confirmationRepository.findOne(confirmation1.getId());

        assertTrue(foundFile.getConfirmations().contains(confirmation1));
        assertTrue(foundFile.getConfirmations().contains(confirmation2));
        assertEquals(foundConfirmation.getFileOfService().getId(), file.getId());
    }

    @Test // если удалчаем File то Confirmation тоже должен удалиться
    public void checkAutoremovingConfirmationIfWeDeleteFileWithConfirmation(){

        FileOfService file = FileOfService.builder()
                                          .originalName("TestFile")
                                          .encodedName("EncodedTestFile")
                                          .type("text/plain")
                                          .url("//hello")
                                          .confirmations(new ArrayList<>())
                                          .build();

        Confirmation confirmation = Confirmation.builder()
                                                .description("confirmation description")
                                                .fileOfService(file)
                                                .build();
        file.getConfirmations().add(confirmation);
        this.fileRepository.save(file);

        this.fileRepository.delete(file);

        Confirmation foundConfirmation = this.confirmationRepository.findOne(confirmation.getId());
        FileOfService foundFile = this.fileRepository.findOne(file.getId());
        assertNull(foundFile);
        assertNull(foundConfirmation);
    }
}
