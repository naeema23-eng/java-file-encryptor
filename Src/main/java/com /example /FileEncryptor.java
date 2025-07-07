package com.example;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class FileEncryptor {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int SALT_LENGTH = 16;
    private static final int IV_LENGTH = 16;
    private static final int KEY_SIZE = 256;
    private static final int ITERATIONS = 65536;

    public static void encryptFile(String password, Path inputFile, Path outputFile) throws Exception {
        byte[] salt = generateRandomBytes(SALT_LENGTH);
        byte[] iv = generateRandomBytes(IV_LENGTH);
        SecretKeySpec key = deriveKey(password, salt);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));

        byte[] inputBytes = Files.readAllBytes(inputFile);
        byte[] encryptedBytes = cipher.doFinal(inputBytes);

        byte[] combined = concatenate(salt, iv, encryptedBytes);
        Files.write(outputFile, combined, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static void decryptFile(String password, Path inputFile, Path outputFile) throws Exception {
        byte[] fileBytes = Files.readAllBytes(inputFile);
        byte[] salt = Arrays.copyOfRange(fileBytes, 0, SALT_LENGTH);
        byte[] iv = Arrays.copyOfRange(fileBytes, SALT_LENGTH, SALT_LENGTH + IV_LENGTH);
        byte[] encryptedBytes = Arrays.copyOfRange(fileBytes, SALT_LENGTH + IV_LENGTH, fileBytes.length);

        SecretKeySpec key = deriveKey(password, salt);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        Files.write(outputFile, decryptedBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private static SecretKeySpec deriveKey(String password, byte[] salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_SIZE);
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(keyBytes, "AES");
    }

    private static byte[] generateRandomBytes(int length) {
        byte[] bytes = new byte[length];
        new SecureRandom().nextBytes(bytes);
        return bytes;
    }

    private static byte[] concatenate(byte[]... arrays) {
        int totalLength = Arrays.stream(arrays).mapToInt(a -> a.length).sum();
        byte[] result = new byte[totalLength];
        int offset = 0;
        for (byte[] array : arrays) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }
}

