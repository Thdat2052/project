package com.example.authservice.services;


import com.example.authservice.dtos.requests.LoginRequest;
import com.example.authservice.dtos.requests.RegisterRequest;
import com.example.common.service.models.requests.TokenValidationRequest;

public interface AuthService {

    String register(RegisterRequest registerDto) throws Exception;

    String login(LoginRequest loginDto);

    boolean validateToken(TokenValidationRequest tokenValidationRequest);
}
