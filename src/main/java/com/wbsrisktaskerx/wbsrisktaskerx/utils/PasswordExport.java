package com.wbsrisktaskerx.wbsrisktaskerx.utils;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.ExportConstants;

import java.security.SecureRandom;

public class PasswordExport {
    private static final String CHARACTERS = ExportConstants.PASSWORD;
    private static final int LENGTH = 8;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generatePassword() {
        StringBuilder password = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            password.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }
}
