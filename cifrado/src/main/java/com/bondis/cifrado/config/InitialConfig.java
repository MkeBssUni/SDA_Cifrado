package com.bondis.cifrado.config;

import com.bondis.cifrado.modules.crypto.service.CryptoService;
import com.bondis.cifrado.modules.users.model.IUserRepository;
import com.bondis.cifrado.modules.users.model.User;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@AllArgsConstructor
public class InitialConfig implements CommandLineRunner {
    private final IUserRepository iUserRepository;
    private final PasswordEncoder encoder;
    private final CryptoService cryptoService;
    @Override
    @Transactional(rollbackFor = {SQLException.class,Exception.class})
    public void run(String... args) throws Exception {
        createUser("admin", "admin@mail.com", "admin1234", "I'm the admin");

    }
    @Transactional(rollbackFor = {SQLException.class,Exception.class})
    public void createUser(String name, String username, String password, String motivation) {
        Optional<User> user = iUserRepository.findByUsername(username);
        if (user.isEmpty()) {
            User newUser = new User();
            newUser.setName(name);
            newUser.setUsername(username);
            newUser.setPassword(encoder.encode(password));
            newUser.setMotivation(motivation);
            iUserRepository.save(newUser);
        }
    }
}
