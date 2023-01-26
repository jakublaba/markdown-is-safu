package com.krabelard.safeapp.service;


import lombok.Builder;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

@Service
public class AesService {

    // used to encrypt note on creating - generates salt and iv
    @SneakyThrows
    public EncryptionParams encrypt(byte[] file, String password) {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        val salt = randomBytes();
        val iv = randomBytes();
        val key = keyFromPassword(password, salt);
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));

        return EncryptionParams.builder()
                .encryptedFile(cipher.doFinal(file))
                .salt(salt)
                .iv(iv)
                .build();
    }

    // used to encrypt note on update - requires previously generated salt and iv
    @SneakyThrows
    public byte[] encrypt(byte[] file, String password, byte[] salt, byte[] iv) {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        val key = keyFromPassword(password, salt);
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));

        return cipher.doFinal(file);
    }

    @SneakyThrows
    public byte[] decrypt(byte[] file, String password, byte[] salt, byte[] iv) {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        val key = keyFromPassword(password, salt);
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));

        return cipher.doFinal(file);
    }

    @SneakyThrows
    private SecretKey keyFromPassword(String password, byte[] salt) {
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        val keySpec = new PBEKeySpec(password.toCharArray(), salt, 1000, 256);

        return new SecretKeySpec(factory.generateSecret(keySpec).getEncoded(), "AES");
    }

    private byte[] randomBytes() {
        val bytes = new byte[16];
        new SecureRandom().nextBytes(bytes);

        return bytes;
    }

    @Builder
    public record EncryptionParams(
            byte[] encryptedFile,
            byte[] salt,
            byte[] iv
    ) {
    }

}
