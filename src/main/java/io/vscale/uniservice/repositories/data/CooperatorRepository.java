package io.vscale.uniservice.repositories.data;

import io.vscale.uniservice.domain.Cooperator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public interface CooperatorRepository extends JpaRepository<Cooperator, Long> {
    Page<Cooperator> findAll(Pageable pageable);
}
