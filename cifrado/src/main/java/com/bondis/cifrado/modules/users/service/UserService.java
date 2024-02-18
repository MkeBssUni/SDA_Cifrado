package com.bondis.cifrado.modules.users.service;

import com.bondis.cifrado.kernel.ResponseApi;
import com.bondis.cifrado.modules.crypto.service.CryptoService;
import com.bondis.cifrado.modules.users.model.IUserRepository;
import com.bondis.cifrado.modules.users.model.User;
import com.bondis.cifrado.modules.users.model.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
    private final IUserRepository iUserRepository;
    private final CryptoService cryptoService;

    @Transactional(readOnly = true)
    public Optional<User> loadUserByUsername(String username) {
        return iUserRepository.findByUsername(username);
    }
    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<User> save (UserDto dto){
        User newUser = new User();
        newUser.setKey(cryptoService.getPublicKey());
        newUser.save(dto);
        return new ResponseApi<>(iUserRepository.saveAndFlush(newUser),HttpStatus.OK, false, "Ok");
    }

}
