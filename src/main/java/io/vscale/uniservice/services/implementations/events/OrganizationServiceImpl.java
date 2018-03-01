package io.vscale.uniservice.services.implementations.events;

import io.vscale.uniservice.domain.Organization;
import io.vscale.uniservice.domain.Student;
import io.vscale.uniservice.repositories.OrganizationRepository;
import io.vscale.uniservice.services.interfaces.events.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 02.03.2018
 *
 * @author Dias Arkharov
 * @version 1.0
 */
@Service
public class OrganizationServiceImpl implements OrganizationService{

    private final OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Set<Student> getHeadOfOrganization(Organization organization) {
        return this.organizationRepository.findOne(organization.getId()).getHeadStudents();
    }
}
