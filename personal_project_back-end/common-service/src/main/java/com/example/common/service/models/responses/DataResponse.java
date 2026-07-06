package com.example.common.service.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataResponse<T> {
    private T data;
    private String message;
    private int statusCode;

    public DataResponse(T data) {
        this.data = data;
    }

    public DataResponse(T data, String message, int statusCode) {
        this.data = data;
        this.message = message;
        this.statusCode = statusCode;
    }

    public static <T> DataResponse<T> ofError(String message, int statusCode) {
        return new DataResponse<T>(null, message, statusCode);
    }
}
