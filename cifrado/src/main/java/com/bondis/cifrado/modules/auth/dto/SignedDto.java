package com.bondis.cifrado.modules.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class    SignedDto {
    private String token;
    private String tokenType;
    private Long userId;
}
