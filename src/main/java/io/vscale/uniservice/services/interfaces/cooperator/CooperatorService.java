package io.vscale.uniservice.services.interfaces.cooperator;

import io.vscale.uniservice.domain.Cooperator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 11.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public interface CooperatorService {
    List<Cooperator> getAllCooperators();
    Page<Cooperator> findAll(Pageable pageable);
}
