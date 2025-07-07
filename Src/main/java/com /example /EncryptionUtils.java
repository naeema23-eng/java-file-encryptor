package com.example;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.CipherOutputStream;
import javax.crypto.CipherInputStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.spec.KeySpec;

public class EncryptionUtils {

    private static final String ENCRYPTION_ALGO = "AES/GCM/NoPadding";
    private static final int KEY_SIZE = 256;
    private static final int IV_SIZE = 12;
    private static final int TAG_LENGTH = 128;
    private static final int ITERATIONS = 65536;
    private static final int SALT_SIZE = 16;

    public static SecretKeySpec getKey(String password, byte[] salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_SIZE);
        byte[] secret = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(secret, "AES");
    }

    public static void encrypt(String password, Path inputPath, Path outputPath) throws Exception {
        byte[] salt = new byte[SALT_SIZE];
        byte[] iv = new byte[IV_SIZE];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        random.nextBytes(iv);

        SecretKeySpec key = getKey(password, salt);
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH, iv));

        FileUtil.createDirectories(outputPath);
        try (FileOutputStream fos = new FileOutputStream(outputPath.toFile());
             CipherOutputStream cos = new CipherOutputStream(fos, cipher);
             FileInputStream fis = new FileInputStream(inputPath.toFile())) {

            fos.write(salt);
            fos.write(iv);
            fis.transferTo(cos);
        }
    }

    public static void decrypt(String password, Path inputPath, Path outputPath) throws Exception {
        try (FileInputStream fis = new FileInputStream(inputPath.toFile())) {
            byte[] salt = fis.readNBytes(SALT_SIZE);
            byte[] iv = fis.readNBytes(IV_SIZE);
            SecretKeySpec key = getKey(password, salt);

            Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGO);
            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH, iv));

            FileUtil.createDirectories(outputPath);
            try (CipherInputStream cis = new CipherInputStream(fis, cipher);
                 FileOutputStream fos = new FileOutputStream(outputPath.toFile())) {

                cis.transferTo(fos);
            }
        }
    }
}
