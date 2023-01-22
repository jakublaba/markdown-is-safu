package com.krabelard.safeapp.service;


import lombok.SneakyThrows;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.AbstractMap;

@Service
public class AesService {

    private final SecretKey key;

    @Autowired
    public AesService() {
        this.key = generateKey();
    }

    @SneakyThrows
    public AbstractMap.SimpleEntry<byte[], byte[]> encrypt(byte[] file) {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        val iv = generateIv();
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));

        return new AbstractMap.SimpleEntry<>(cipher.doFinal(file), iv);
    }

    @SneakyThrows
    public byte[] encrypt(byte[] file, byte[] iv) {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));

        return cipher.doFinal(file);
    }

    @SneakyThrows
    public byte[] decrypt(byte[] file, byte[] iv) {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));

        return cipher.doFinal(file);
    }

    @SneakyThrows
    private SecretKey generateKey() {
        val keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);

        return keyGenerator.generateKey();
    }

    private byte[] generateIv() {
        val iv = new byte[16];
        new SecureRandom().nextBytes(iv);

        return iv;
    }

}
