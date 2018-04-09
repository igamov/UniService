package io.vscale.uniservice.services.interfaces.auth;

import io.vscale.uniservice.domain.User;
import org.springframework.security.core.Authentication;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public interface AuthenticationService {

    User getUserByAuthentication(Authentication authentication);

}
