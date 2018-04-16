package io.vscale.uniservice.services.implementations.admin;

import io.vscale.uniservice.domain.RoleType;
import io.vscale.uniservice.domain.User;
import io.vscale.uniservice.forms.rest.NewUserForm;
import io.vscale.uniservice.repositories.data.UserRepository;
import io.vscale.uniservice.repositories.indexing.UserESRepository;
import lombok.AllArgsConstructor;

import io.vscale.uniservice.security.states.UserState;
import io.vscale.uniservice.services.interfaces.admin.UserAdminService;
import io.vscale.uniservice.services.interfaces.auth.RoleTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserAdminServiceImpl implements UserAdminService {

    private UserRepository userRepository;
    private UserESRepository userESRepository;
    private RoleTypeService roleTypeService;

    @Override
    public void addNewUserREST(NewUserForm newUserForm) {

        Optional<RoleType> roleType = this.roleTypeService.getRoleTypeByRole(newUserForm.getRole());

        if(!roleType.isPresent()){
            throw new IllegalArgumentException();
        }

        User user = User.builder()
                        .login(newUserForm.getLogin())
                        .password(newUserForm.getPassword())
                        .state(UserState.CONFIRMED)
                        .build();

        this.userRepository.save(user);
        this.userESRepository.save(user);

    }
}
