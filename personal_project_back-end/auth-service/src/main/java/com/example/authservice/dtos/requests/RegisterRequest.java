package com.example.authservice.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {


    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Email is required")
    private String email;
    private String fullName;
    @NotBlank(message = "CCCD is required")
    private String cccd;
    public RegisterRequest() {
    }

    public RegisterRequest(String username, String password, String email, String fullName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
    }

}
