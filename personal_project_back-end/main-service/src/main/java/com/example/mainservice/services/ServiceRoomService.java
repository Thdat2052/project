package com.example.mainservice.services;

import com.example.common.service.entities.ServiceRoomEntity;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.models.ServiceModel;
import com.example.common.service.services.CommonService;

import java.util.List;

public interface ServiceRoomService extends CommonService<ServiceRoomEntity, Long> {
    List<ServiceRoomEntity> createServiceOfRoom(Long contractId, List<ServiceModel> services) throws AppException;
    List<ServiceModel> findByContractId(Long id) throws AppException;
}
