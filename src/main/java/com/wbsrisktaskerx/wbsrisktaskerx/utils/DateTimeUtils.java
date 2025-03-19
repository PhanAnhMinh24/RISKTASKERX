package com.wbsrisktaskerx.wbsrisktaskerx.utils;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    private static final ZoneId DEFAULT_ZONE = ZoneOffset.UTC;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");

    public static OffsetDateTime getDateTimeNow() {
        return OffsetDateTime.now(DEFAULT_ZONE);
    }

    public static String formatDateTime(OffsetDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(FORMATTER);
    }

    public static OffsetDateTime parseDateTime(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.isEmpty()) {
            return null;
        }
        return OffsetDateTime.parse(dateTimeString, FORMATTER);
    }
}