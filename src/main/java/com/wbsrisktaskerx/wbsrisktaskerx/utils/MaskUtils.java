package com.wbsrisktaskerx.wbsrisktaskerx.utils;

import com.querydsl.core.util.StringUtils;

public class MaskUtils {

    public static String mask(String value) {
        if (StringUtils.isNullOrEmpty(value)) {
            return value;
        }
        int length = value.length();
        if (length <= 3) {
            return value;
        }
        return value.substring(0, 3) + "*".repeat(length - 3);
    }
}

