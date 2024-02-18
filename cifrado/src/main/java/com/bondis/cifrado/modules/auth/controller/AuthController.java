package com.bondis.cifrado.modules.auth.controller;

import com.bondis.cifrado.kernel.ResponseApi;
import com.bondis.cifrado.modules.auth.dto.SignedDto;
import com.bondis.cifrado.modules.auth.dto.UserSigninDto;
import com.bondis.cifrado.modules.auth.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"*"})
@AllArgsConstructor
public class AuthController {
    private final AuthService service;
    @PostMapping("/")
    public ResponseEntity<ResponseApi<SignedDto>> signIn(@RequestBody UserSigninDto userSigninDto) {
        return service.signIn(userSigninDto);
    }
}
