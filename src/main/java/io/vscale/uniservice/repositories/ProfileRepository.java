package io.vscale.uniservice.repositories;

import io.vscale.uniservice.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
