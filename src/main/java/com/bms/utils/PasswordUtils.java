package com.bms.utils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Properties;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.bms.config.DatabaseConfig;

public class PasswordUtils {
	private static final int ITERATIONS = 10;
    private static final int KEY_LENGTH = 256;
    
    public static String hashPassword(String password) {
        try {
        	Properties properties = new Properties();
    		properties.load(DatabaseConfig.class.getClassLoader().getResourceAsStream("db.properties"));
    		final String SALT = properties.getProperty("SALT");
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), SALT.getBytes(), ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    
}
