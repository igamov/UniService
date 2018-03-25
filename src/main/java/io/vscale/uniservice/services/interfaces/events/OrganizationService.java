package io.vscale.uniservice.services.interfaces.events;

import io.vscale.uniservice.domain.Organization;
import io.vscale.uniservice.domain.Student;

import java.util.List;
import java.util.Set;

/**
 * 02.03.2018
 *
 * @author Dias Arkharov
 * @version 1.0
 */
public interface OrganizationService {

    Set<Student> getHeadOfOrganization(Organization organization);

    List<Organization> findAll();

    Organization findById(Long id);

    void delete(Long id);

    void delete(Organization organization);

    void save(Organization organization);

}
