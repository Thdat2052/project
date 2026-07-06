package com.example.common.service.utils;

import java.text.Normalizer;

public class StringUtils {
    public static String formatFileName(String fileName) {
        String normalizedFileName = Normalizer.normalize(fileName, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .replaceAll("[^a-zA-Z0-9.-]", "_");

        normalizedFileName = normalizedFileName.trim();

        normalizedFileName = normalizedFileName.replaceAll("_+", "_");

        return normalizedFileName;
    }

    public static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}