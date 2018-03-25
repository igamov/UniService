package io.vscale.uniservice.repositories.data;

import io.vscale.uniservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);
    Optional<User> findByToken(String token);

}
