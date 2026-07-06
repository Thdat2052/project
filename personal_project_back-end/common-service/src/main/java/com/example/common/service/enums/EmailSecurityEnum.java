package com.example.common.service.enums;

public enum EmailSecurityEnum {
    NONE("NONE"),
    SSL_TLS("SSL/TLS");

    private final String value;

    EmailSecurityEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
