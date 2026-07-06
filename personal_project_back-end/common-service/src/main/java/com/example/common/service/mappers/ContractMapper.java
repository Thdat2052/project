package com.example.common.service.mappers;

import com.example.common.service.entities.ContractEntity;
import com.example.common.service.models.ContractModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContractMapper {
    ContractMapper INSTANCE = Mappers.getMapper(ContractMapper.class);

    ContractEntity toEntity(ContractModel model);
    ContractModel toModel(ContractEntity entity);
}
