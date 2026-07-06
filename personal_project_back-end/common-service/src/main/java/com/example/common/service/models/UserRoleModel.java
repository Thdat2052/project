package com.example.common.service.models;

import com.example.common.service.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleModel {
    private Long userId;
    private RoleEnum name;
}
