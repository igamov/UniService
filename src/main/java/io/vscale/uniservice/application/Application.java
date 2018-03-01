package io.vscale.uniservice.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 01.03.2018
 *
 * @author UniServiceTeam
 * @version 1.0
 */
@SpringBootApplication
@ComponentScan({"io.vscale.uniservice.application", "io.vscale.uniservice.controllers", "io.vscale.uniservice.domain",
                "io.vscale.uniservice.repositories", "io.vscale.uniservice.security", "io.vscale.uniservice.services",
                "io.vscale.uniservice.validators"})
@EnableJpaRepositories(basePackages = "io.vscale.uniservice.repositories")
@EntityScan(basePackages = "io.vscale.uniservice.domain")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
