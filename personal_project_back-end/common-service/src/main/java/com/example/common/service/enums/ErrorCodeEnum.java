package com.example.common.service.enums;

public enum ErrorCodeEnum {

    CATEGORY_NAME_REQUIRED("1000", "Category name is required!"),
    CATEGORY_NAME_LENGTH_EXCEED("1001", "Category name exceed 100!"),


    //list error for email
    EMAIL_SEND("2002", "Email sending error!"),
    EMAIL_ATTACHMENT_NOT_FOUND("2003", "Email attachment not found!");
    private final String code;
    private final String message;

    ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }


    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
