package io.vscale.uniservice.services.implementations.auth.rest;

import io.vscale.uniservice.domain.User;
import io.vscale.uniservice.repositories.data.UserRepository;
import io.vscale.uniservice.security.rest.filters.TokenAuthentication;
import io.vscale.uniservice.services.interfaces.auth.AuthenticationService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 01.04.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Service("restAuthenticationService")
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserRepository userRepository;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @SneakyThrows
    public User getUserByAuthentication(Authentication authentication) {

        TokenAuthentication tokenAuthentication = (TokenAuthentication) authentication;
        String username = (String) tokenAuthentication.getPrincipal();
        String password = (String) tokenAuthentication.getCredentials();

        StringBuilder sb1 = new StringBuilder();
        sb1.append(username).append(password);

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(sb1.toString().getBytes("UTF-8"));
        byte byteData[] = md.digest();

        String token = IntStream.range(0, byteData.length)
                                .mapToObj(i -> Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1))
                                .collect(Collectors.joining());

        Optional<User> existedUser = this.userRepository.findByToken(token);
        if(!existedUser.isPresent()){
            throw new BadCredentialsException("Wrong password or login");
        }

        return existedUser.get();

    }
}
