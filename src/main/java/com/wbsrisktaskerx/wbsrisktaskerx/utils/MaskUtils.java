package com.wbsrisktaskerx.wbsrisktaskerx.utils;

import org.apache.commons.lang3.StringUtils;

public class MaskUtils {

    public static String mask(String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        }
        int length = value.length();
        if (length <= 3) {
            return value;
        }
        return value.substring(0, 3) + "*".repeat(length - 3);
    }
}

