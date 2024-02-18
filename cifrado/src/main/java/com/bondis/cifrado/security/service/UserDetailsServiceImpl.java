package com.bondis.cifrado.security.service;

import com.bondis.cifrado.modules.users.model.User;
import com.bondis.cifrado.modules.users.service.UserService;
import com.bondis.cifrado.security.entities.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService service;

    public UserDetailsServiceImpl(UserService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {

            Optional<User> user = Optional.ofNullable(service.loadUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("UserNotFound")));
            if (user.isPresent()) {
                return UserDetailsImpl.build(user.get());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
