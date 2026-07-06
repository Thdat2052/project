package com.example.common.service.exceptions;

import com.example.common.service.models.responses.DataResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String,String>> handleParseError(HttpMessageNotReadableException ex) {
        String msg = "JSON parse error: " + ex.getMostSpecificCause().getMessage();
        return ResponseEntity
                .badRequest()
                .body(Map.of("error", msg));
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<DataResponse> handleBaseException(AppException ex) {
        var errorModel = ex.getErrorModel();
        var messageError = errorModel !=null ? ex.getErrorModel().getMessage() : ex.getMessage();
        DataResponse dataResponse = new DataResponse<>(null, messageError,  HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(dataResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DataResponse> handleQuantityNotEnoughException(MethodArgumentNotValidException  ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringBuilder errorMessageBuilder = new StringBuilder();
        for (FieldError error : fieldErrors) {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errorMessageBuilder.append(fieldName).append(": ").append(errorMessage).append(", ");
        }
        String errorMessage = errorMessageBuilder.toString().replaceAll(", $", "");

        return new ResponseEntity<>(DataResponse.ofError(errorMessage,  HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
