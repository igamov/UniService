package io.vscale.uniservice.services.implementations.auth.general;

import io.vscale.uniservice.domain.RoleType;
import io.vscale.uniservice.repositories.data.RoleTypeRepository;
import io.vscale.uniservice.services.interfaces.auth.RoleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Service
public class RoleTypeServiceImpl implements RoleTypeService {

    private final RoleTypeRepository roleTypeRepository;

    @Autowired
    public RoleTypeServiceImpl(RoleTypeRepository roleTypeRepository) {
        this.roleTypeRepository = roleTypeRepository;
    }

    @Override
    public Optional<RoleType> getRoleTypeByRole(String role) {

        List<RoleType> roles = this.roleTypeRepository.findAll();

        return roles.stream()
                    .filter(roleType -> roleType.toString().equals(role))
                    .findFirst();

    }
}
