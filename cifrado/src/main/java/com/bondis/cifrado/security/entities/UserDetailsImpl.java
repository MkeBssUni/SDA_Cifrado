package com.bondis.cifrado.security.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImp implements UserDetails {
    private final String email;
    private final String password;
    private final String key;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImp(String email, String password, String key, Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.password = password;
        this.key = key;
        this.authorities = authorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }


    public String getEmail() {
        return email;
    }
    public String getKey(){return key;}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
