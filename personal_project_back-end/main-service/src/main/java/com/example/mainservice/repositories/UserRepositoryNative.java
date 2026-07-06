package com.example.mainservice.repositories;

import com.example.common.service.entities.UserEntity;
import com.example.common.service.models.UserRoleModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserRepositoryNative {
    Page<UserEntity> findAllByPaging(PagingQueryConditionRequest pageRequest);
    List<UserRoleModel> findAllUserRoleByUserIdIn(List<Long> userIds);
}
