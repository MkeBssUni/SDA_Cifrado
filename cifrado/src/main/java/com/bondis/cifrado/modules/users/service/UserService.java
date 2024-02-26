package com.bondis.cifrado.modules.users.service;

import com.bondis.cifrado.kernel.ResponseApi;
import com.bondis.cifrado.modules.crypto.service.CryptoService;
import com.bondis.cifrado.modules.hash.service.HashService;
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
    private final HashService hashService;

    @Transactional(readOnly = true)
    public Optional<User> loadUserByUsername(String username) {
        return iUserRepository.findByUsername(username);
    }
    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<User> save (UserDto dto) throws Exception {
        User newUser = new User();

        //cifrar el dto
//        dto.setName(hashService.encrypt(dto.getName()));
//        dto.setUsername(hashService.encrypt(dto.getUsername()));
//        dto.setPassword(hashService.encrypt(dto.getPassword()));
//        dto.setMotivation(hashService.encrypt(dto.getMotivation()));

        //descifrar la información que viene:
        dto.setName(hashService.decrypt(dto.getName()));
        dto.setUsername(hashService.decrypt(dto.getUsername()));
        dto.setMotivation(hashService.decrypt(dto.getMotivation()));
        

        newUser.save(dto);

        newUser = iUserRepository.saveAndFlush(newUser);

        newUser.setUsername(hashService.encrypt(newUser.getUsername()));
        newUser.setPassword(hashService.encrypt(newUser.getPassword()));
        newUser.setName(hashService.encrypt(newUser.getName()));
        newUser.setMotivation(hashService.encrypt(newUser.getMotivation()));

        return new ResponseApi<>(newUser,HttpStatus.OK, false, "Ok");
    }

}
