package io.vscale.uniservice.security.rest.filters;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 25.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public class TokenAuthentication extends AbstractAuthenticationToken {

    private final String token;

    public TokenAuthentication(String token) {
        super(null);
        this.token = token;
    }

    public TokenAuthentication(String token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return token.split(":")[1];
    }

    @Override
    public Object getPrincipal() {
        return token.split(":")[0];
    }

    public String getToken() {
        return this.token;
    }

}
