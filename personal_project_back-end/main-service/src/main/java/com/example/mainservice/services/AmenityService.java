package com.example.mainservice.services;

import com.example.common.service.entities.ServiceEntity;
import com.example.common.service.models.ServiceModel;
import com.example.common.service.services.CommonService;

import java.util.List;

public interface AmenityService extends CommonService<ServiceEntity, Long> {
    long countByIdIn(List<Long> ids);
    ServiceModel createOrUpdate(ServiceModel entityInformation);
}
