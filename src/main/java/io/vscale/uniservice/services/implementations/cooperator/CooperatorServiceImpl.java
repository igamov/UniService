package io.vscale.uniservice.services.implementations.cooperator;

import io.vscale.uniservice.domain.Cooperator;
import io.vscale.uniservice.repositories.data.CooperatorRepository;
import io.vscale.uniservice.services.interfaces.cooperator.CooperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 11.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Service
public class CooperatorServiceImpl implements CooperatorService{

    private final CooperatorRepository cooperatorRepository;

    @Autowired
    public CooperatorServiceImpl(CooperatorRepository cooperatorRepository) {
        this.cooperatorRepository = cooperatorRepository;
    }

    @Override
    public List<Cooperator> getAllCooperators() {
        return this.cooperatorRepository.findAll();
    }
}
