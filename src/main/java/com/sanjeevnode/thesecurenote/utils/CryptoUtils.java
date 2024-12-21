package com.sanjeevnode.thesecurenote.utils;

import com.sanjeevnode.thesecurenote.dto.note.NoteContentEncryptDTO;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class CryptoUtils {
    private static final int KEY_LENGTH = 256;
    private static final int ITERATIONS = 100_000;
    private static final int GCM_TAG_LENGTH = 128;
    private static final int NONCE_LENGTH = 12;
    private static final String KEY_ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final String ENCRYPTION_ALGORITHM = "AES";

    // Derive a salt from a string
    public static byte[] deriveSalt(String saltString) {
        return saltString.getBytes(StandardCharsets.UTF_8);
    }

    // Derive a 256-bit AES key using PBKDF2
    public static SecretKey deriveKey(String password, String saltString) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), deriveSalt(saltString), ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(keyBytes, ENCRYPTION_ALGORITHM);
    }

    // Encrypt text using AES-GCM
    public static String encrypt(String plainText, SecretKey key) throws Exception {
        // Generate a random nonce
        byte[] nonce = new byte[NONCE_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(nonce);

        // Initialize AES-GCM cipher
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, nonce);
        cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);

        // Perform encryption
        byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        // Concatenate nonce and ciphertext, and encode as Base64
        byte[] combined = new byte[nonce.length + cipherText.length];
        System.arraycopy(nonce, 0, combined, 0, nonce.length);
        System.arraycopy(cipherText, 0, combined, nonce.length, cipherText.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    public static String decrypt(String encryptedText, SecretKey key) throws Exception {
        // Decode Base64 input
        byte[] combined = Base64.getDecoder().decode(encryptedText);

        // Extract nonce and ciphertext
        byte[] nonce = new byte[NONCE_LENGTH];
        System.arraycopy(combined, 0, nonce, 0, NONCE_LENGTH);
        byte[] cipherText = new byte[combined.length - NONCE_LENGTH];
        System.arraycopy(combined, NONCE_LENGTH, cipherText, 0, cipherText.length);

        // Initialize AES-GCM cipher
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, nonce);
        cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);

        // Perform decryption
        byte[] plainTextBytes = cipher.doFinal(cipherText);
        return new String(plainTextBytes, StandardCharsets.UTF_8);
    }

    public static String encrypt(NoteContentEncryptDTO n) throws Exception {
        return encrypt(n.content(), deriveKey(n.masterPin(), n.saltString()));
    }

    public static String decrypt(NoteContentEncryptDTO n) throws Exception {
        return decrypt(n.content(), deriveKey(n.masterPin(), n.saltString()));
    }

}
