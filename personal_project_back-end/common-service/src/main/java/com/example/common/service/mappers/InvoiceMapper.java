package com.example.common.service.mappers;

import com.example.common.service.entities.ContractEntity;
import com.example.common.service.entities.InvoiceEntity;
import com.example.common.service.models.ContractModel;
import com.example.common.service.models.InvoiceModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);

    InvoiceEntity toEntity(InvoiceModel model);
    InvoiceModel toModel(InvoiceEntity entity);
}
