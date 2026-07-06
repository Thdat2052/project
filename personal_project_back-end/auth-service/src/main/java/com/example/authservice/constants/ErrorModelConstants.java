package com.example.authservice.constants;

import com.example.common.service.models.ErrorModel;
import org.springframework.http.HttpStatus;

public class ErrorModelConstants {
    private ErrorModelConstants() {
    }

    public static ErrorModel USER_NOTFOUND = ErrorModel.of(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
            "User not found!");
    public static ErrorModel ROLE_NOTFOUND = ErrorModel.of(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
            "Role not found!");
}
