package com.ecsail.Gybe.utils;

public class PasswordValidator {

    public static boolean validatePassword(String password1, String password2) {
        if (password1 == null) {
            return false;
        }
        // Check if the password1 is at least 10 characters long
        if (password1.length() < 10) {
            return false;
        }
        // Check for at least one uppercase letter
        if (!password1.matches(".*[A-Z].*")) {
            return false;
        }
        // Check if the passwords match
        if (!password1.equals(password2)) {
            return false;
        }
        // Check for at least one special character
        if (!password1.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            return false;
        }
        return true; // Passes all checks
    }

    public static String passwordError(String password1, String password2) {
        // StringBuilder to collect error messages
        StringBuilder errorMessage = new StringBuilder("Invalid Password: ");

        // Check if the password is null
        if (password1 == null) {
            errorMessage.append("Password cannot be null. ");
        }

        // Check if the passwords match
        if (!password1.equals(password2)) {
            errorMessage.append("Passwords do not match. ");
        }

        // Check if the password is at least 10 characters long
        if (password1 != null && password1.length() < 10) {
            errorMessage.append("Password must be at least 10 characters long. ");
        }

        // Check for at least one uppercase letter
        if (password1 != null && !password1.matches(".*[A-Z].*")) {
            errorMessage.append("Password must contain at least one uppercase letter. ");
        }

        // Check for at least one special character
        if (password1 != null && !password1.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            errorMessage.append("Password must contain at least one special character. ");
        }
            return errorMessage.toString();
    }

}

