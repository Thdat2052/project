package com.example.authservice.mapper;

import com.example.authservice.dtos.requests.RegisterRequest;
import com.example.common.service.entities.UserEntity;
import com.example.common.service.enums.RoleEnum;
import com.example.common.service.models.UserModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper extends com.example.common.service.mappers.UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(registerRequest.getPassword()))")
    UserEntity toEntity(RegisterRequest registerRequest, PasswordEncoder passwordEncoder);

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(userModel.getPassword()))")
    UserEntity toEntity(UserModel userModel, PasswordEncoder passwordEncoder);

}
