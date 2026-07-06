package com.example.authservice.services;

import com.example.common.service.entities.UserRoleEntity;
import com.example.common.service.services.CommonService;

import java.util.List;


public interface UserRoleService extends CommonService<UserRoleEntity, Long> {

    List<UserRoleEntity> findAllByUserId(Long userId);
    void deleteByUserIdIn(List<Long> userIds);
}
