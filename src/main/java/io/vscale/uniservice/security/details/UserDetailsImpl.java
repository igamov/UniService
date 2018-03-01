package io.vscale.uniservice.security.details;

import io.vscale.uniservice.domain.User;
import io.vscale.uniservice.security.states.UserState;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 01.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public class UserDetailsImpl implements UserDetails {

    private User user;

    private UserDetailsImpl(){}

    public UserDetailsImpl(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.user.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.toString()))
                        .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.user.getState().equals(UserState.DELETED);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.user.getState().equals(UserState.BANNED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.user.getState().equals(UserState.NOT_CONFIRMED);
    }

    @Override
    public boolean isEnabled() {
        return this.user.getState().equals(UserState.CONFIRMED);
    }

    public User getUser(){
        return this.user;
    }
}
