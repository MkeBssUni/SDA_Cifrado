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
import javax.crypto.spec.IvParameterSpec;

@Service
public class HashService {

    // Obtiene el valor de la propiedad 'hash.secret' desde el archivo de configuración en la variable 'secretKey'.
    @Value("${hash.secret}")
    private String secretKey;
    // Define el algoritmo de cifrado a utilizar: AES con modo CBC y relleno PKCS5Padding.
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    // Vector de inicialización (IV) estático, compuesto por 16 bytes en cero. En un entorno de producción, se recomienda usar un IV aleatorio y único para cada cifrado.
    private static final byte[] IV = new byte[16];
    // Método privado para generar una clave secreta AES a partir de la cadena de texto 'secretKey'.
    private SecretKeySpec generateSecretKeyFromString() throws NoSuchAlgorithmException {
        // Utiliza SHA-256 para obtener un hash de la clave secreta, asegurando una longitud adecuada y distribución uniforme.
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha.digest(secretKey.getBytes(StandardCharsets.UTF_8));
        // Trunca o ajusta el hash a 128 bits (16 bytes), adecuado para AES.
        keyBytes = Arrays.copyOf(keyBytes, 16);
        // Genera y retorna la especificación de la clave secreta para AES.
        return new SecretKeySpec(keyBytes, "AES");
    }

    // Método para cifrar un texto plano, utilizando la clave secreta y el vector de inicialización definidos.
    public String encrypt(String plaintext) throws Exception {
        // Genera la clave secreta a partir de la cadena proporcionada.
        SecretKey key = generateSecretKeyFromString();
        // Obtiene una instancia del cifrador para el algoritmo especificado.
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // Especifica los parámetros del vector de inicialización.
        IvParameterSpec ivParams = new IvParameterSpec(IV);
        // Inicializa el cifrador en modo de encriptación con la clave secreta y los parámetros de IV.
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParams);
        // Realiza la encriptación del texto plano y lo convierte a un arreglo de bytes.
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        // Codifica el arreglo de bytes en Base64 y lo retorna como una cadena de texto.
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedText) throws Exception {
        // Genera la clave secreta a partir de la cadena proporcionada.
        SecretKey key = generateSecretKeyFromString();
        // Obtiene una instancia del cifrador para el algoritmo especificado.
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // Especifica los parámetros del vector de inicialización.
        IvParameterSpec ivParams = new IvParameterSpec(IV);
        // Inicializa el cifrador en modo de desencriptación con la clave secreta y los parámetros de IV.
        cipher.init(Cipher.DECRYPT_MODE, key, ivParams);
        // Decodifica la cadena de texto en Base64 y desencripta los bytes resultantes.
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        // Retorna el texto plano desencriptado, convirtiendo el arreglo de bytes a una cadena de texto.
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
