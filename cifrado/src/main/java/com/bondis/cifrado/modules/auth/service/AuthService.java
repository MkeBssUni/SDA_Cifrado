package com.bondis.cifrado.modules.auth.service;

import com.bondis.cifrado.kernel.ResponseApi;
import com.bondis.cifrado.modules.auth.dto.SignedDto;
import com.bondis.cifrado.modules.auth.dto.UserSigninDto;
import com.bondis.cifrado.modules.users.model.User;
import com.bondis.cifrado.modules.users.service.UserService;
import com.bondis.cifrado.security.jwt.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AuthService {
    private final UserService service;
    private final JwtProvider provider;
    private final String prefix = "Bearer";
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<ResponseApi<SignedDto>> signIn(UserSigninDto userSigninDto){
        try {
            Optional<User> userOptional = service.loadUserByUsername(userSigninDto.getUsernameOrEmail());
            if (userOptional.isEmpty())
                return new ResponseEntity<>(new ResponseApi<>(null, HttpStatus.NOT_FOUND, true, "NotFound"), HttpStatus.BAD_REQUEST);
            User user = userOptional.get();
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userSigninDto.getUsernameOrEmail(), userSigninDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(auth);
            String token = provider.generateToken(auth);
            SignedDto signedDto = new SignedDto(token, prefix,user.getKey(),user.getId());
            signedDto.setUserId(user.getId());

            return new ResponseEntity<>(new ResponseApi<>(signedDto, HttpStatus.OK, false, "ok"), HttpStatus.OK);
        } catch (Exception e) {
            String message = "CredentialsMismatch";
            return new ResponseEntity<>(new ResponseApi<>(null, HttpStatus.UNAUTHORIZED, true, message), HttpStatus.BAD_REQUEST);
        }
    }
}
