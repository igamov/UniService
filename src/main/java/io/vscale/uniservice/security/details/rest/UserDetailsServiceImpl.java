package io.vscale.uniservice.security.details.rest;

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
 * 25.03.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service("restUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {

        Optional<User> existedUser = this.userRepository.findByToken(token);

        if(!existedUser.isPresent()){
            throw new IllegalArgumentException("User not found by token <" + token + ">");
        }

        return new UserDetailsImpl(existedUser.get());

    }
}
