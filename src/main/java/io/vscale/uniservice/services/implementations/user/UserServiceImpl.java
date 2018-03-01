package io.vscale.uniservice.services.implementations.user;

import lombok.AllArgsConstructor;

import io.vscale.uniservice.domain.RoleType;
import io.vscale.uniservice.domain.User;
import io.vscale.uniservice.repositories.UserRepository;
import io.vscale.uniservice.services.interfaces.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public List<User> getUsersByRole(RoleType roleType) {

        List<User> currentUsers = this.userRepository.findAll();

        return currentUsers.stream()
                           .filter(user -> user.getRoles().equals(roleType))
                           .collect(Collectors.toList());

    }

}
