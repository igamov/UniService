package io.vscale.uniservice.services.implementations.events;

import io.vscale.uniservice.domain.ConfirmationLimits;
import io.vscale.uniservice.repositories.data.ConfirmationLimitsRepository;
import io.vscale.uniservice.services.interfaces.events.ConfirmationLimitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 01.03.2018
 *
 * @author Aynur Aymurzin
 * @version 1.0
 */
@Service
public class ConfirmationLimitsServiceImpl implements ConfirmationLimitsService {

    private ConfirmationLimitsRepository repository;

    @Autowired
    public ConfirmationLimitsServiceImpl(ConfirmationLimitsRepository repository){
        this.repository = repository;
    }

    @Override
    public List<ConfirmationLimits> findAllBySemesterNumber(Long semesterNumber) {
        return repository.findAllBySemesterNumber(semesterNumber);
    }
}
