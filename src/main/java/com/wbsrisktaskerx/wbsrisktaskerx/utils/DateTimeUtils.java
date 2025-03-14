package com.wbsrisktaskerx.wbsrisktaskerx.utils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    public static LocalDateTime getDateTimeNow() {
        return LocalDateTime.now();
    }
}