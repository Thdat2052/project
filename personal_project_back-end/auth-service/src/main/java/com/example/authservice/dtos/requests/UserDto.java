package com.example.authservice.dtos.requests;

import com.example.common.service.enums.RoleEnum;
import lombok.Data;


@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private Long roleId;
    private RoleEnum roleName;
}
