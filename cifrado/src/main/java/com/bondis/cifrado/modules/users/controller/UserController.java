package com.bondis.cifrado.modules.users.controller;

import com.bondis.cifrado.kernel.ResponseApi;
import com.bondis.cifrado.modules.hash.service.HashService;
import com.bondis.cifrado.modules.users.model.User;
import com.bondis.cifrado.modules.users.model.UserDto;
import com.bondis.cifrado.modules.users.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"*"})
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final HashService hashService;

    @PostMapping("/")
    public ResponseEntity<ResponseApi<User>> save (@Validated(UserDto.Save.class) @RequestBody UserDto dto) throws Exception {
        ResponseApi<User> responseApi = userService.save(dto);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }
    @GetMapping("/paged/")
    public ResponseEntity<?> getPaged(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam(defaultValue = "id", required = false) String sort,
            @RequestParam(defaultValue = "asc", required = false) String direction) throws Exception {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        ResponseApi<Page<User>> responseApi = userService.getPaged(pageable);

        return new ResponseEntity<>(responseApi,responseApi.getStatus());
    }

}
