package io.vscale.uniservice.repositories.data;

import io.vscale.uniservice.domain.Confirmation;
import io.vscale.uniservice.domain.FileOfService;
import io.vscale.uniservice.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 01.03.2018
 *
 * @author Aynur Aymurzin
 * @version 1.0
 */
public interface ConfirmationRepository extends JpaRepository<Confirmation, Long> {

    List<Confirmation> findAllByDescription(String description);
    List<Confirmation> findAllByFileOfService(FileOfService file);
    List<Confirmation> findAllByProfile(Profile profile);
    Confirmation findOneByFileOfService(FileOfService file);

}
