package com.example;

import org.junit.jupiter.api.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

public class FileEncryptorTest {

    private final Path testFile = Paths.get("test-data/test.txt");
    private final Path encryptedFile = Paths.get("test-data/test.txt.enc");
    private final Path decryptedFile = Paths.get("test-data/test_decrypted.txt");
    private final String password = "test123";

    @BeforeEach
    void setup() throws Exception {
        Files.createDirectories(testFile.getParent());
        Files.writeString(testFile, "This is a test file.");
    }

    @AfterEach
    void cleanup() throws Exception {
        Files.deleteIfExists(testFile);
        Files.deleteIfExists(encryptedFile);
        Files.deleteIfExists(decryptedFile);
    }

    @Test
    void testEncryptionAndDecryption() throws Exception {
        EncryptionUtils.encrypt(password, testFile, encryptedFile);
        assertTrue(Files.exists(encryptedFile));

        EncryptionUtils.decrypt(password, encryptedFile, decryptedFile);
        assertTrue(Files.exists(decryptedFile));

        String decryptedContent = Files.readString(decryptedFile);
        assertEquals("This is a test file.", decryptedContent);
    }
}
