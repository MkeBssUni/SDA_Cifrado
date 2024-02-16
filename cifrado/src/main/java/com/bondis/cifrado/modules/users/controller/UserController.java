package com.bondis.cifrado.modules.users.controller;

import com.bondis.cifrado.kernel.ResponseApi;
import com.bondis.cifrado.modules.users.model.User;
import com.bondis.cifrado.modules.users.model.UserDto;
import com.bondis.cifrado.modules.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"*"})
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<ResponseApi<User>> save (@Validated(UserDto.Save.class) @RequestBody UserDto dto){
        ResponseApi<User> responseApi = userService.save(dto);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }

}
