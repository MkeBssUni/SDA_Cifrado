package com.bondis.cifrado.modules.auth.dto;

import lombok.Data;

@Data
public class UserSigninDto {
    private String usernameOrEmail;
    private String password;
}
