package io.vscale.uniservice.repositories.data;

import io.vscale.uniservice.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 02.03.2018
 *
 * @author Dias Arkharov
 * @version 1.0
 */
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    @Query(value = "SELECT * FROM organization ORDER BY title ASC LIMIT 4 OFFSET :number", nativeQuery = true)
    List<Organization> findAllByOrderByTitleAsc(@Param("number") Long number);

    @Query(value = "SELECT * FROM organization ORDER BY title DESC LIMIT 4 OFFSET :number", nativeQuery = true)
    List<Organization> findAllByOrderByTitleDesc(@Param("number") Long number);

}
