package com.ecsail.Gybe.utils;

public class PasswordValidator {

    public static boolean validatePassword(String password, String password2) {
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

        if (!password.equals(password2)) {
            return false;
        }
        // Check for at least one special character
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            return false;
        }
        return true; // Passes all checks
    }

    public static String passwordError(String password, String password2) {
        // StringBuilder to collect error messages
        StringBuilder errorMessage = new StringBuilder("Invalid Password: ");

        // Check if the password is null
        if (password == null) {
            errorMessage.append("Password cannot be null. ");
        }

        // Check if the passwords match
        if (!password.equals(password2)) {
            errorMessage.append("Passwords do not match. ");
        }

        // Check if the password is at least 10 characters long
        if (password != null && password.length() < 10) {
            errorMessage.append("Password must be at least 10 characters long. ");
        }

        // Check for at least one uppercase letter
        if (password != null && !password.matches(".*[A-Z].*")) {
            errorMessage.append("Password must contain at least one uppercase letter. ");
        }

        // Check for at least one special character
        if (password != null && !password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            errorMessage.append("Password must contain at least one special character. ");
        }
            return errorMessage.toString();
    }

}

