package com.example.mainservice.services.impls;

import com.example.common.service.entities.ServiceEntity;
import com.example.common.service.enums.ServiceTypeEnum;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.mappers.ServiceMapper;
import com.example.common.service.models.ServiceModel;
import com.example.common.service.services.impls.CommonServiceImpl;
import com.example.common.service.utils.EntityUtils;
import com.example.mainservice.constants.ErrorModelConstants;
import com.example.mainservice.repositories.ServiceRepository;
import com.example.mainservice.services.AmenityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AmenityServiceImpl extends CommonServiceImpl<ServiceEntity, Long, ServiceRepository>
        implements AmenityService {
    protected AmenityServiceImpl(ServiceRepository repo) {
        super(repo);
    }

    @Override
    public long countByIdIn(List<Long> ids) {
        return repo.countByIdIn(ids);
    }

    @Override
    public ServiceModel createOrUpdate(ServiceModel entityInformation) {
        Long id = entityInformation.getId();
        if(!EntityUtils.isCreate(id)) {
            var entityExisted  = repo.findById(entityInformation.getId()).
                    orElseThrow(() -> new AppException(ErrorModelConstants.SERVICE_NOTFOUND));
            entityInformation.setCreatedAt(entityExisted.getCreatedAt());
            entityInformation.setUpdatedAt(entityExisted.getUpdatedAt());
        }
        var serviceType = entityInformation.getServiceType() != null ? entityInformation.getServiceType():
                ServiceTypeEnum.OTHER;
        entityInformation.setServiceType(serviceType);
        ServiceEntity entity = ServiceMapper.INSTANCE.toEntity(entityInformation);
        ServiceEntity entityUpdated = repo.save(entity);
        var data = ServiceMapper.INSTANCE.toModel(entityUpdated);
        return data;
    }

}
