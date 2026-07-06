package com.example.common.service.mappers;

import com.example.common.service.entities.CheckoutRequestEntity;
import com.example.common.service.entities.InvoiceEntity;
import com.example.common.service.models.CheckoutRequestModel;
import com.example.common.service.models.InvoiceModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CheckoutRequestMapper {
    CheckoutRequestMapper INSTANCE = Mappers.getMapper(CheckoutRequestMapper.class);
    CheckoutRequestEntity toEntity(CheckoutRequestModel model);
    CheckoutRequestModel toModel(CheckoutRequestEntity entity);
}
