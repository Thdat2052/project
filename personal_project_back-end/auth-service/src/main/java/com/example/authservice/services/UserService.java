package com.example.authservice.services;

import com.example.authservice.dtos.requests.UserDto;
import com.example.common.service.entities.UserEntity;
import com.example.common.service.models.UserModel;
import com.example.common.service.services.CommonService;

import java.util.List;
import java.util.Optional;

public interface UserService extends CommonService<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    UserModel create(UserModel user);
    List<UserModel> createAll(List<UserModel> users, String transactionId);
    List<UserDto> findUserInformation(String username);
    List<UserEntity> findByTransactionId(String transactionId);
    Boolean rollBackWhenCreatedContractFail(String transactionId);
    UserModel getInfoFromToken(String token);
}
