package com.smartresume.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtil {

    private static final int ITERATIONS = 600_000;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 16;

    private PasswordUtil() {
    }

    public static String hashPassword(String password) {

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        byte[] salt = new byte[SALT_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);

        byte[] hash = generateHash(password, salt);

        return Base64.getEncoder().encodeToString(salt)
                + ":"
                + Base64.getEncoder().encodeToString(hash);
    }

    public static boolean verifyPassword(
            String password,
            String storedPassword) {

        if (password == null
                || storedPassword == null
                || storedPassword.isBlank()) {
            return false;
        }

        try {
            String[] parts = storedPassword.split(":");

            if (parts.length != 2) {
                return false;
            }

            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] storedHash = Base64.getDecoder().decode(parts[1]);

            byte[] calculatedHash = generateHash(password, salt);

            return java.security.MessageDigest.isEqual(
                    storedHash,
                    calculatedHash
            );

        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static byte[] generateHash(
            String password,
            byte[] salt) {

        try {

            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    salt,
                    ITERATIONS,
                    KEY_LENGTH
            );

            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            return factory.generateSecret(spec).getEncoded();

        } catch (NoSuchAlgorithmException
                 | InvalidKeySpecException e) {

            throw new IllegalStateException(
                    "Unable to hash password",
                    e
            );
        }
    }
}