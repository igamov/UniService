package io.vscale.uniservice.repositories;

import io.vscale.uniservice.domain.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public interface RoleTypeRepository extends JpaRepository<RoleType, Long> {
}
