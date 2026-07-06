package com.example.common.service.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class MoneyFormatUtil {
    private static final NumberFormat CURRENCY_FORMAT =
            NumberFormat.getInstance(new Locale("vi", "VN"));

    public static String format(Integer amount) {
        if (amount == null) return "0";
        return CURRENCY_FORMAT.format(amount);
    }

    public static String format(Long amount) {
        if (amount == null) return "0";
        return CURRENCY_FORMAT.format(amount);
    }

    public static String format(double amount) {
        return CURRENCY_FORMAT.format(amount);
    }
}
