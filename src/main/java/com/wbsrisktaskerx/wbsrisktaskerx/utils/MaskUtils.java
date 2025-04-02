package com.wbsrisktaskerx.wbsrisktaskerx.utils;

public class MaskUtils {

    public static String mask(String value, int visibleChars) {
        if (value == null) {
            return null;
        }
        int length = value.length();
        if (length <= visibleChars) {
            return value;
        }
        return value.substring(0, visibleChars) + "*".repeat(length - visibleChars);
    }
}

