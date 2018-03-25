package io.vscale.uniservice.services.interfaces.events;

import io.vscale.uniservice.domain.Confirmation;
import io.vscale.uniservice.domain.FileOfService;
import io.vscale.uniservice.domain.Profile;

import java.util.List;

/**
 * 01.03.2018
 *
 * @author Aynur Aymurzin
 * @version 1.0
 */
public interface ConfirmationService {
    List<Confirmation> findAllByDescription(String description);
    List<Confirmation> findAllByFile(FileOfService file);
    List<Confirmation> findAllByProfile(Profile profile);
    void save(Confirmation confirmation);
}
