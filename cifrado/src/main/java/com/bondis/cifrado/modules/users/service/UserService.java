package com.bondis.cifrado.modules.users.service;

import com.bondis.cifrado.kernel.ResponseApi;
import com.bondis.cifrado.modules.hash.service.HashService;
import com.bondis.cifrado.modules.users.model.IUserRepository;
import com.bondis.cifrado.modules.users.model.User;
import com.bondis.cifrado.modules.users.model.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    // Inyecta el servicio de cifrado.
    private final HashService hashService;

    @Transactional(readOnly = true)
    public Optional<User> loadUserByUsername(String username) {
        return iUserRepository.findByUsername(username);
    }
    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<User> save (UserDto dto) throws Exception {
        User newUser = new User();

        //descifrar la información que viene:
        dto.setName(hashService.decrypt(dto.getName()));
        dto.setUsername(hashService.decrypt(dto.getUsername()));
        dto.setMotivation(hashService.decrypt(dto.getMotivation()));
        dto.setFavNumber(Long.parseLong(hashService.decrypt(dto.getFavNumberString())));

        newUser.save(dto);

        newUser = iUserRepository.saveAndFlush(newUser);

        newUser.setUsername(hashService.encrypt(newUser.getUsername()));

        return new ResponseApi<>(newUser,HttpStatus.OK, false, "Ok");
    }

    @Transactional(readOnly = true)
    public ResponseApi<Page<User>> getPaged(Pageable pageable) throws Exception {
        Page<User> listOfUsers = iUserRepository.findAll(pageable);
        return new ResponseApi<>(listOfUsers, HttpStatus.OK,false,"Ok");
    }

}
