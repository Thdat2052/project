package com.example.common.service.mappers;

import com.example.common.service.entities.UserEntity;
import com.example.common.service.entities.UtilityIndexEntity;
import com.example.common.service.models.UserModel;
import com.example.common.service.models.UtilityIndexModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UtilityIndexMapper {

    UtilityIndexMapper INSTANCE = Mappers.getMapper(UtilityIndexMapper.class);

    UtilityIndexEntity toEntity(UtilityIndexModel model);

    UtilityIndexModel toModel(UtilityIndexEntity entity);
    List<UtilityIndexModel> toModelList(List<UtilityIndexEntity> userEntities);
    List<UtilityIndexEntity> toEntityList(List<UtilityIndexModel> userModels);
}
