package com.example.mainservice.services.impls;

import com.example.common.service.entities.ServiceRoomEntity;
import com.example.common.service.enums.ServiceRoomStatusEnum;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.models.ServiceModel;
import com.example.common.service.services.impls.CommonServiceImpl;
import com.example.mainservice.constants.ErrorModelConstants;
import com.example.mainservice.repositories.ServiceRoomRepository;
import com.example.mainservice.services.AmenityService;
import com.example.mainservice.services.ServiceRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceRoomServiceImpl extends CommonServiceImpl<ServiceRoomEntity, Long, ServiceRoomRepository>
        implements ServiceRoomService {
    protected ServiceRoomServiceImpl(ServiceRoomRepository repo) {
        super(repo);
    }

    @Autowired
    private AmenityService service;

    @Override
    @Transactional
    public List<ServiceRoomEntity> createServiceOfRoom(Long contractId, List<ServiceModel> services) throws AppException {
        List<ServiceRoomEntity>  serviceRoomEntities = new ArrayList<>();
        var serviceIds = services.stream().map(serviceModel -> serviceModel.getId()).toList();
        if(serviceIds.isEmpty()) return  serviceRoomEntities;
        long existingCount = service.countByIdIn(serviceIds);
        if(existingCount != serviceIds.size()) {
            throw new AppException(ErrorModelConstants.SERVICE_NOTFOUND);
        }
        for(ServiceModel service : services){
            ServiceRoomEntity serviceRoomEntity = new ServiceRoomEntity();
            serviceRoomEntity.setServiceId(service.getId());
            serviceRoomEntity.setQuantity(service.getQuantity());
            serviceRoomEntity.setContractId(contractId);
            serviceRoomEntity.setPrice(service.getPrice()!=null?service.getPrice() : 0L);
            serviceRoomEntity.setStatus(ServiceRoomStatusEnum.ACTIVE);
            serviceRoomEntities.add(serviceRoomEntity);
        }
        return repo.saveAll(serviceRoomEntities);
    }

    @Override
    public List<ServiceModel> findByContractId(Long contractId) throws AppException {
        return repo.findByContractId(contractId);
    }
}
