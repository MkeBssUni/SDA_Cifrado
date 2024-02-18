package com.bondis.cifrado.security.entities;

import com.bondis.cifrado.modules.users.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {
    private final String username;
    private final String password;
    private final String key;

    public UserDetailsImpl(String username, String password, String key) {
        this.username = username;
        this.password = password;
        this.key = key;
    }
    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(user.getUsername(), user.getPassword(),user.getKey());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
    public String getKey(){return key;}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
