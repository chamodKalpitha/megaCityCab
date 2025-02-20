package com.bms.utils;

import java.util.regex.Pattern;

public class InputValidator {


    public static void isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if(!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }


    public static void isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");

        }
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpperCase = true;
            if (Character.isLowerCase(c)) hasLowerCase = true;
            if (Character.isDigit(c)) hasDigit = true;
        }

        if(!hasUpperCase && hasLowerCase && hasDigit) {
            throw new IllegalArgumentException("Password must contain one uppercase letter, one lowercase letter, and one digit.");
        };
    }
    
    public static void isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^[0-9]{10}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        if (!pattern.matcher(phoneNumber).matches()) {
            throw new IllegalArgumentException("Invalid phone number format. It must be exactly 10 digits.");
        }
    }
}
