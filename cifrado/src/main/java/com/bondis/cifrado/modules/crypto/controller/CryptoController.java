package com.bondis.cifrado.modules.crypto.controller;

import com.bondis.cifrado.modules.crypto.service.CryptoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/crypto")
@CrossOrigin(origins = {"*"})
@AllArgsConstructor
public class CryptoController {
    private CryptoService cryptoService;

    @GetMapping("/publicKey")
    public String getPublicKey(){
        return "Clave pública: "+cryptoService.getPublicKey();
    }
    @PostMapping("/encrypt")
    public String encryptData(@RequestBody String data) throws Exception {
        return "Cifrado: "+cryptoService.encryptData(data);
    }
    @PostMapping("/decrypt")
    public String decryptData(@RequestBody String encryptedData) throws Exception {
        return "Descifrado: "+cryptoService.decryptData(encryptedData);
    }
}
