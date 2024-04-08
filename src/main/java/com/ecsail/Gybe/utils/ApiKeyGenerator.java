package com.ecsail.Gybe.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class ApiKeyGenerator {
    public static String generateApiKey(int byteLength) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[byteLength];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}