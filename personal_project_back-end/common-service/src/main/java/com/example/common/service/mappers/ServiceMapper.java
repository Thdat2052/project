package com.example.common.service.mappers;

import com.example.common.service.entities.ServiceEntity;
import com.example.common.service.models.ServiceModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    ServiceMapper INSTANCE = Mappers.getMapper(ServiceMapper.class);

    ServiceEntity toEntity(ServiceModel model);
    ServiceModel toModel(ServiceEntity entity);

    List<ServiceModel> toModelList(List<ServiceEntity> entities);
    List<ServiceEntity> toEntityList(List<ServiceModel> models);
}
