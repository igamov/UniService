package io.vscale.uniservice.security.details.general;

import io.vscale.uniservice.domain.User;
import io.vscale.uniservice.repositories.data.UserRepository;
import io.vscale.uniservice.security.details.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Service("generalUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Optional<User> existedUser = this.userRepository.findByLogin(login);

        if(!existedUser.isPresent()){
            throw new IllegalArgumentException("User not found by login <" + login + ">");
        }

        return new UserDetailsImpl(existedUser.get());

    }

}
