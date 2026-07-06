package com.example.common.service.mappers;

import com.example.common.service.entities.ContractEntity;
import com.example.common.service.entities.MaintenanceRequestEntity;
import com.example.common.service.models.ContractModel;
import com.example.common.service.models.MaintenanceRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MaintenanceRequestMapper {
    MaintenanceRequestMapper INSTANCE = Mappers.getMapper(MaintenanceRequestMapper.class);

    MaintenanceRequestEntity toEntity(MaintenanceRequestModel model);
    MaintenanceRequestModel toModel(MaintenanceRequestEntity entity);
}
