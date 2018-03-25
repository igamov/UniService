package io.vscale.uniservice.services.interfaces.auth;

import io.vscale.uniservice.domain.RoleType;

import java.util.Optional;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public interface RoleTypeService {

    Optional<RoleType> getRoleTypeByRole(String role);

}
