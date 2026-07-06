package com.example.authservice.services;

import com.example.common.service.entities.RoleEntity;
import com.example.common.service.enums.RoleEnum;
import com.example.common.service.services.CommonService;

import java.util.List;


public interface RoleService extends CommonService<RoleEntity, Long> {

    List<RoleEntity> findAllByIdIn(List<Long> roleIds);
    RoleEntity create(RoleEnum role);
    RoleEntity findByName(RoleEnum name);
}
