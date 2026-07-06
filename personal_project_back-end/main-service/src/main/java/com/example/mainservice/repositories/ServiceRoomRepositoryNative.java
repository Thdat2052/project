package com.example.mainservice.repositories;

import com.example.common.service.entities.ServiceRoomEntity;
import com.example.common.service.models.ServiceModel;

import java.util.List;

public interface ServiceRoomRepositoryNative {
    List<ServiceModel> findAllByRoomId(Long roomId);
    List<ServiceModel> findByContractId(Long contractId);
}
