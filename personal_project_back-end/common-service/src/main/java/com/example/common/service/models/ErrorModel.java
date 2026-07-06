package com.example.common.service.models;


import com.example.common.service.enums.ErrorCodeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
public class ErrorModel implements Serializable
{

    private String code;
    private String error;
    private String message;
    private String description;
    private String errorField;

    private ErrorModel(String code, String error, String message, String description, String errorField)
    {
        this.code = code;
        this.error = error;
        this.message = message;
        this.description = description;
        this.errorField = errorField;
    }

    public static ErrorModel of(String code, String error, String message)
    {
        return of(code, error, message, null);
    }

    public static ErrorModel of(String code, String error, String message, String description)
    {
        return of(code, error, message, description, null);
    }

    public static ErrorModel of(String code, String error, String message, String description, String errorField)
    {
        return new ErrorModel(code, error, message, description, errorField);
    }

    public static ErrorModel of(ErrorCodeEnum errorCodeEnum, String error, String message)
    {
        return of(errorCodeEnum.getCode(), error, message, null);
    }

    public static ErrorModel of(String code, String message)
    {
        return of(code, null, message, null);
    }

}
