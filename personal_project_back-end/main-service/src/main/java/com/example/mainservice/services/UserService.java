package com.example.mainservice.services;

import com.example.common.service.entities.UserEntity;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.models.UtilityIndexModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.models.UserModel;
import com.example.common.service.services.CommonService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService extends CommonService<UserEntity, Long> {
    UserModel createOrUpdate(UserModel model) throws Exception;
    Page<UserModel> findAllWithPagingUsingJpa(PagingQueryConditionRequest pagingQueryConditionRequest);
    UserModel findMe() throws AppException;
    List<UserEntity> findByIdIn(List<Long> ids);
    UserModel updateMe(UserModel model) throws AppException;
    void resetPassword(String username) throws AppException;
}
