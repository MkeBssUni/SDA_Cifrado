package com.bondis.cifrado.modules.crypto.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;

@Service
@AllArgsConstructor
public class CryptoService {
    private KeyPair keyPair;

    public String getPublicKey(){
        // Obtener la clave pública en formato Base64
        byte[] encodedPublicKey = keyPair.getPublic().getEncoded();
        return Base64.getEncoder().encodeToString(encodedPublicKey);
    }

    public String encryptData(String data) throws Exception {
        System.out.println("este es el string sin cifrar: "+data);
        PublicKey publicKey = keyPair.getPublic();
        // Obtener una instancia de Cipher con el algoritmo RSA
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        // Cifrar los datos
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());

        // Devolver los datos cifrados en formato Base64
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
    public String decryptData(String encryptedData) throws Exception {
        System.out.println("esta es la cadena cifrada: "+encryptedData);
        PrivateKey privateKey = keyPair.getPrivate();

        // Obtener una instancia de Cipher con el algoritmo RSA
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        // Decodificar los datos cifrados desde Base64
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);

        // Descifrar los datos
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        // Devolver los datos descifrados como una cadena
        return new String(decryptedBytes);
    }
}
