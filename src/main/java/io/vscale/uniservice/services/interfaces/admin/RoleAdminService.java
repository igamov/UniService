package io.vscale.uniservice.services.interfaces.admin;

import io.vscale.uniservice.domain.RoleType;
import io.vscale.uniservice.forms.RoleForm;

import java.util.List;

/**
 * 11.03.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface RoleAdminService {

    RoleType getRoleById(Long id);
    List<RoleType> getRolesOfService();
    void removeRoleFromUser(RoleForm roleForm);

}
