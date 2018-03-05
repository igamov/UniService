package io.vscale.uniservice.services;

import io.vscale.uniservice.application.Application;
import io.vscale.uniservice.domain.Confirmation;
import io.vscale.uniservice.services.interfaces.events.ConfirmationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 05.03.2018
 *
 * @author Aynur Aymurzin
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ConfirmationIntegrationTest {

    @Autowired
    private ConfirmationService confirmationService;

    @Test
    public void findAllByDescription() throws Exception {
        // Создаем 2 Confirmation отправляем их в бд и проверяем существуют ли они там
        Confirmation conf = Confirmation.builder()
                                        .description("confirmed1")
                                        .build();
        Confirmation conf2 = Confirmation.builder()
                                         .description("confirmed1")
                                         .build();
        this.confirmationService.save(conf);
        this.confirmationService.save(conf2);
        List<Confirmation> confirmationList = this.confirmationService.findAllByDescription("confirmed1");

        assertEquals(2, confirmationList.size());
        assertTrue(confirmationList.contains(conf));
        assertTrue(confirmationList.contains(conf2));
    }

}
