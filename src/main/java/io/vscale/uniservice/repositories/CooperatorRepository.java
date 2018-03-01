package io.vscale.uniservice.repositories;

import io.vscale.uniservice.domain.Cooperator;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public interface CooperatorRepository extends JpaRepository<Cooperator, Long> {
}
