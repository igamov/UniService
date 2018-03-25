package io.vscale.uniservice.services.implementations.events;

import io.vscale.uniservice.domain.Confirmation;
import io.vscale.uniservice.domain.FileOfService;
import io.vscale.uniservice.domain.Profile;
import io.vscale.uniservice.repositories.data.ConfirmationRepository;
import io.vscale.uniservice.services.interfaces.events.ConfirmationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 01.03.2018
 *
 * @author Aynur Aymurzin
 * @version 1.0
 */
@Service
public class ConfirmationServiceImpl implements ConfirmationService {

    private ConfirmationRepository confirmationRepository;

    public ConfirmationServiceImpl(ConfirmationRepository confirmationRepository){
        this.confirmationRepository = confirmationRepository;
    }
    @Override
    public List<Confirmation> findAllByDescription(String description) {
        return confirmationRepository.findAllByDescription(description);
    }

    @Override
    public List<Confirmation> findAllByFile(FileOfService file) {
        return confirmationRepository.findAllByFileOfService(file);
    }

    @Override
    public List<Confirmation> findAllByProfile(Profile profile) {
        return confirmationRepository.findAllByProfile(profile);
    }

    @Override
    public void save(Confirmation confirmation) {
        confirmationRepository.save(confirmation);
    }
}
