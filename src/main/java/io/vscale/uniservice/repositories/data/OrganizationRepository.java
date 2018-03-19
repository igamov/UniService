package io.vscale.uniservice.repositories.data;

import io.vscale.uniservice.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 02.03.2018
 *
 * @author Dias Arkharov
 * @version 1.0
 */
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
