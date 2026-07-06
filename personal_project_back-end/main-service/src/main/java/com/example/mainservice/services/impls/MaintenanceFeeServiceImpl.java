package com.example.mainservice.services.impls;

import com.example.common.service.entities.MaintenanceFeeEntity;
import com.example.common.service.entities.MaintenanceRequestEntity;
import com.example.common.service.enums.ContractStatusEnum;
import com.example.common.service.enums.MaintenanceRequestStatusEnum;
import com.example.common.service.enums.ServiceRoomStatusEnum;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.mappers.MaintenanceRequestMapper;
import com.example.common.service.models.MaintenanceRequestModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.services.impls.CommonServiceImpl;
import com.example.mainservice.constants.ErrorModelConstants;
import com.example.mainservice.models.requests.MaintenanceRequestRequest;
import com.example.mainservice.models.responses.MaintenanceRequestResponse;
import com.example.mainservice.repositories.MaintenanceFeeRepository;
import com.example.mainservice.repositories.MaintenanceRequestRepository;
import com.example.mainservice.repositories.ServiceRoomRepository;
import com.example.mainservice.services.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MaintenanceFeeServiceImpl extends CommonServiceImpl<MaintenanceFeeEntity, Long, MaintenanceFeeRepository>
        implements MaintenanceFeeService {
    public MaintenanceFeeServiceImpl(MaintenanceFeeRepository repository) {
        super(repository);
    }

}
