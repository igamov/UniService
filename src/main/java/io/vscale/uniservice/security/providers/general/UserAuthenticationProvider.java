package io.vscale.uniservice.security.providers.general;

import io.vscale.uniservice.domain.User;
import io.vscale.uniservice.repositories.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Component("generalUserAuthentication")
public class UserAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;

    @Autowired
    public UserAuthenticationProvider(UserRepository userRepository,
                                      @Qualifier("generalUserDetailsService") UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
    }

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<User> existedUser = this.userRepository.findByLogin(username);

        if(!existedUser.isPresent()){
            throw new BadCredentialsException("Wrong password or login");
        }

        existedUser.ifPresent(user -> {

            if(!passwordEncoder.matches(password, user.getPassword())){

                if(passwordEncoder.matches(password, user.getTempPassword())){

                    user.setTempPassword(null);
                    this.userRepository.save(user);

                }else{
                    throw new BadCredentialsException("Wrong password or login");
                }

            }

        });

        UserDetails details = this.userDetailsService.loadUserByUsername(username);
        Collection<? extends GrantedAuthority> authorities = details.getAuthorities();
        return new UsernamePasswordAuthenticationToken(details, password, authorities);

    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.getName().equals(UsernamePasswordAuthenticationToken.class.getName());
    }

}
