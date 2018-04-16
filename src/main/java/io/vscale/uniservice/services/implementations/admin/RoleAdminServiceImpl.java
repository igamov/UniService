package io.vscale.uniservice.services.implementations.admin;

import io.vscale.uniservice.domain.RoleType;
import io.vscale.uniservice.domain.User;
import io.vscale.uniservice.forms.rest.RoleForm;
import io.vscale.uniservice.repositories.data.RoleTypeRepository;
import io.vscale.uniservice.repositories.data.UserRepository;
import io.vscale.uniservice.services.interfaces.admin.RoleAdminService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 11.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RoleAdminServiceImpl implements RoleAdminService{

    private UserRepository userRepository;
    private RoleTypeRepository roleTypeRepository;

    @Override
    public RoleType getRoleById(Long id) {
        return this.roleTypeRepository.findOne(id);
    }

    @Override
    public List<RoleType> getRolesOfService() {
        return this.roleTypeRepository.findAll();
    }

    @Override
    public void removeRoleFromUser(RoleForm roleForm) {

        User user = this.userRepository.findOne(roleForm.getUserId());
        RoleType roleType = this.roleTypeRepository.findOne(roleForm.getRoleId());

        user.getRoles()
            .removeIf(role -> role.equals(roleType));

    }
}
