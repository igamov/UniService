package io.vscale.uniservice.repositories.data;

import io.vscale.uniservice.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findBySurnameAndName(String surname, String name);

}
