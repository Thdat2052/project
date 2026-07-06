package com.example.common.service.models.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ApiResponse<T> extends DataResponse<T> {
    private final Map<String, List<String>> headers;

    public ApiResponse(T data, String message, int statusCode, Map<String, List<String>> headers) {
        super(data, message, statusCode);
        this.headers = headers;
    }

    public ApiResponse(T data, int statusCode, Map<String, List<String>> headers) {
        super(data, "", statusCode);
        this.headers = headers;
    }

    public ApiResponse(T data, int statusCode, String message) {
        super(data, message, statusCode);
        this.headers = null;
    }

    public ApiResponse(T data, Map<String, List<String>> headers) {
        super(data);
        this.headers = headers;
    }

}
