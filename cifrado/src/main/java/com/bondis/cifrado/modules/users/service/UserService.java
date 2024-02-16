package com.bondis.cifrado.modules.users.service;

import com.bondis.cifrado.kernel.ResponseApi;
import com.bondis.cifrado.modules.users.model.IUserRepository;
import com.bondis.cifrado.modules.users.model.User;
import com.bondis.cifrado.modules.users.model.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
    private final IUserRepository iUserRepository;

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseApi<User> save (UserDto dto){
        System.out.println("este es el dto que llega: "+dto.toString());
        return new ResponseApi<>(HttpStatus.OK, false, "Ok");
    }

}
