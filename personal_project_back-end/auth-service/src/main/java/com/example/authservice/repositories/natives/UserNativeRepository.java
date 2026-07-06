package com.example.authservice.repositories.natives;

import com.example.authservice.dtos.requests.UserDto;

import java.util.List;

public interface UserNativeRepository {
    List<UserDto> findUserInformation(String username);
}
