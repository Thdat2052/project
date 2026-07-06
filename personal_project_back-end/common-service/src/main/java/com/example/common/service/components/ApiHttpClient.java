package com.example.common.service.components;

import com.alibaba.fastjson.JSON;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.models.ErrorModel;
import com.example.common.service.models.responses.ApiResponse;
import com.example.common.service.utils.JsonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;

@Component
public class ApiHttpClient {

    private final HttpClient httpClient;

    public ApiHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    // Synchronous GET request with TypeReference
    public <T> ApiResponse<T> get(String url, Map<String, String> headers, Map<String, Object> queryParams, TypeReference<T> responseType) throws AppException, IOException, InterruptedException {
        HttpRequest request = buildRequest(url, "GET", headers, queryParams, null);
        return executeRequest(request, responseType);
    }

    // Asynchronous GET request with TypeReference
    public <T> CompletableFuture<ApiResponse<T>> getAsync(String url, Map<String, String> headers, Map<String, Object> queryParams, TypeReference<T> responseType) {
        HttpRequest request = buildRequest(url, "GET", headers, queryParams, null);
        return executeRequestAsync(request, responseType);
    }

    // Synchronous POST request with TypeReference
    public <T> ApiResponse<T> post(String url, Map<String, String> headers, Map<String, Object> queryParams, Object body, TypeReference<T> responseType) throws AppException, IOException, InterruptedException {
        HttpRequest request = buildRequest(url, "POST", headers, queryParams, body);
        return executeRequest(request, responseType);
    }

    // (Tương tự cho put, delete, putAsync, deleteAsync)

    private HttpRequest buildRequest(String url, String method, Map<String, String> headers, Map<String, Object> queryParams, Object body) {
        if (queryParams != null && !queryParams.isEmpty()) {
            String queryString = buildQueryString(queryParams);
            url = url + "?" + queryString;
        }

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method(method, body != null ? HttpRequest.BodyPublishers.ofString(JSON.toJSONString(body)) : HttpRequest.BodyPublishers.noBody());

        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.putIfAbsent("Content-Type", "application/json");
        headers.forEach(requestBuilder::header);

        return requestBuilder.build();
    }

    private String buildQueryString(Map<String, Object> queryParams) {
        StringJoiner joiner = new StringJoiner("&");
        for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
            String encodedKey = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8);
            String encodedValue = entry.getValue() != null
                    ? URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8)
                    : "";
            joiner.add(encodedKey + "=" + encodedValue);
        }
        return joiner.toString();
    }

    private <T> ApiResponse<T> executeRequest(HttpRequest request, TypeReference<T> responseType) throws AppException,
            IOException, InterruptedException {
//        try {
//            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//            return processResponse(response, responseType);
//        } catch (Exception e) {
//            return new ApiResponse<>(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
//        }
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return processResponse(response, responseType);
    }

    private <T> CompletableFuture<ApiResponse<T>> executeRequestAsync(HttpRequest request, TypeReference<T> responseType) {
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    try {
                        return processResponse(response, responseType);
                    } catch (AppException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private <T> ApiResponse<T> processResponse(HttpResponse<String> response, TypeReference<T> responseType) throws AppException {
        int statusCode = response.statusCode();
        String responseBody = response.body();
        if (statusCode >= 200 && statusCode < 300) {
            T data = deserialize(responseBody, responseType);
            return new ApiResponse<>(data, statusCode, response.headers().map());
        } else {
            ErrorModel errorModel = ErrorModel.of(String.valueOf(statusCode), null, responseBody);
            throw new AppException(errorModel);
        }
    }

    private <T> T deserialize(String responseBody, TypeReference<T> responseType) {
        return JsonUtils.deserialize(responseBody, responseType);
    }
}