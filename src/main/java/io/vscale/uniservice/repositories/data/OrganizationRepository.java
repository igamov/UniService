package io.vscale.uniservice.repositories.data;

import io.vscale.uniservice.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 02.03.2018
 *
 * @author Dias Arkharov
 * @version 1.0
 */
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    List<Organization> findAllByOrderByTitleAsc();
    List<Organization> findAllByOrderByTitleDesc();

}
