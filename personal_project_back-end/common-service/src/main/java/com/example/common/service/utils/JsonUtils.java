package com.example.common.service.utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtils() {
    }
    static {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    public static <T> T deserialize(String responseBody, TypeReference<T> responseType) {
        try {
            return objectMapper.readValue(responseBody, responseType);
        } catch (IOException e) {
            throw new RuntimeException("Failed to deserialize JSON: " + e.getMessage(), e);
        }
    }
}