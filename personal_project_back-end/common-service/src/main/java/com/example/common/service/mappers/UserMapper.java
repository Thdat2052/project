package com.example.common.service.mappers;

import com.example.common.service.entities.UserEntity;
import com.example.common.service.models.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity toEntity(UserModel model);

    UserModel toModel(UserEntity entity);
    List<UserModel> toModelList(List<UserEntity> userEntities);
    List<UserEntity> toEntityList(List<UserModel> userModels);
}
