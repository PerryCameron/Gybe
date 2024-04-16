package com.ecsail.Gybe.utils;

public class PasswordValidator {

    public static boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }
        // Check if the password is at least 10 characters long
        if (password.length() < 10) {
            return false;
        }
        // Check for at least one uppercase letter
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }
        // Check for at least one special character
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            return false;
        }
        return true; // Passes all checks
    }

}

