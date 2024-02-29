package com.bondis.cifrado.modules.hash.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
//
//@Service
//public class HashService {
//
//    @Value("${hash.secret}")
//    private String secretKey;
//    private static final String ALGORITHM = "AES";
//
//    public String encrypt(String plaintext) throws Exception {
//        SecretKey key = generateSecretKeyFromString();
//        Cipher cipher = Cipher.getInstance(ALGORITHM);
//        cipher.init(Cipher.ENCRYPT_MODE, key);
//        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
//        return Base64.getEncoder().encodeToString(encryptedBytes);
//    }
//
//    public String decrypt(String encryptedText) throws Exception {
//        SecretKey key = generateSecretKeyFromString();
//        Cipher cipher = Cipher.getInstance(ALGORITHM);
//        cipher.init(Cipher.DECRYPT_MODE, key);
//        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
//        return new String(decryptedBytes);
//    }
//    public SecretKey generateSecretKeyFromString() throws NoSuchAlgorithmException, UnsupportedEncodingException {
//        MessageDigest sha = MessageDigest.getInstance("SHA-256");
//        byte[] keyBytes = sha.digest(secretKey.getBytes(StandardCharsets.UTF_8));
//
//        // Solo utilizar los primeros 128 bits para claves AES de 128 bits
//        keyBytes = Arrays.copyOf(keyBytes, 16);
//
//        // Crear y devolver la SecretKey
//        return new SecretKeySpec(keyBytes, "AES");
//    }
//}

import javax.crypto.spec.IvParameterSpec;

@Service
public class HashService {

    @Value("${hash.secret}")
    private String secretKey;
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final byte[] IV = new byte[16]; // IV estático, todo ceros. En producción, debería ser aleatorio y único por mensaje.

    public String encrypt(String plaintext) throws Exception {
        SecretKey key = generateSecretKeyFromString();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        IvParameterSpec ivParams = new IvParameterSpec(IV);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParams);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedText) throws Exception {
        SecretKey key = generateSecretKeyFromString();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        IvParameterSpec ivParams = new IvParameterSpec(IV);
        cipher.init(Cipher.DECRYPT_MODE, key, ivParams);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    private SecretKeySpec generateSecretKeyFromString() throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha.digest(secretKey.getBytes(StandardCharsets.UTF_8));
        keyBytes = Arrays.copyOf(keyBytes, 16); // Utilizar solo los primeros 128 bits (16 bytes)
        return new SecretKeySpec(keyBytes, "AES");
    }
}
