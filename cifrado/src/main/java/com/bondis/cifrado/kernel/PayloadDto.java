package com.bondis.cifrado.kernel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PayloadDto {
    private Long user_id;
    private String criptedData;
}
