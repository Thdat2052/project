package com.example.paymentservice.utils;

import com.example.paymentservice.utils.cryptos.CryptoUtils;

import java.util.*;

public class VNPayUtils {
    public static String hashAllFields(Map<String, String> fields, String secretKey) {
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder sb = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                sb.append(fieldName).append("=").append(fieldValue);
                if (itr.hasNext()) {
                    sb.append("&");
                }
            }
        }
        return CryptoUtils.hmacSHA512(secretKey, sb.toString());
    }
}