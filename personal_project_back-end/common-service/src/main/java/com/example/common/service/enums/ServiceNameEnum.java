package com.example.common.service.enums;

public enum ServiceNameEnum {
    AUTH_SERVICE("auth"),
    MAIN_SERVICE("main"),
    FILE_SERVICE("file"),
    NOTIFY_SERVICE("notify"),
    PAYMENT_SERVICE("payment");

    private final String key;

    ServiceNameEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}