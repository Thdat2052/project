package com.example.common.service.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String formatFull(LocalDateTime dateTime) {
        if (dateTime == null) return "";
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    public static String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) return "";
        return dateTime.format(DATE_FORMATTER);
    }
}
