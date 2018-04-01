package io.vscale.uniservice.services.implementations.auth.general;

import io.vscale.uniservice.domain.User;
import io.vscale.uniservice.repositories.data.UserRepository;
import io.vscale.uniservice.security.details.UserDetailsImpl;
import io.vscale.uniservice.services.interfaces.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Service("generalAuthenticationService")
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserRepository userRepository;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByAuthentication(Authentication authentication) {

        UserDetailsImpl currentUserDetails = (UserDetailsImpl) authentication.getPrincipal();
        User currentUserModel = currentUserDetails.getUser();
        Long currentUserId = currentUserModel.getId();

        return this.userRepository.findOne(currentUserId);

    }
}
