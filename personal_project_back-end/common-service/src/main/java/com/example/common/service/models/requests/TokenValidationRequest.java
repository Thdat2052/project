package com.example.common.service.models.requests;

import com.example.common.service.enums.RoleEnum;
import lombok.Data;

import java.util.List;

@Data
public class TokenValidationRequest {
    private String token;
    private List<RoleEnum> roleNeeded;
}
