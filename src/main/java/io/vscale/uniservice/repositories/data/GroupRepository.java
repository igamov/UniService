package io.vscale.uniservice.repositories.data;

import io.vscale.uniservice.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByTitle(String title);
}
