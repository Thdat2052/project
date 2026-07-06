package com.example.mainservice.services;

import com.example.common.service.entities.MaintenanceRequestEntity;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.models.MaintenanceRequestModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.services.CommonService;
import com.example.mainservice.models.requests.MaintenanceRequestRequest;
import com.example.mainservice.models.responses.MaintenanceRequestResponse;
import org.springframework.data.domain.Page;

public interface MaintenanceRequestService extends CommonService<MaintenanceRequestEntity, Long> {
    MaintenanceRequestModel create(MaintenanceRequestModel model) throws AppException;

    Page<MaintenanceRequestResponse> findAllWithPaging(PagingQueryConditionRequest request);

    MaintenanceRequestModel findById(Long id) throws AppException;

    MaintenanceRequestModel update(Long id, MaintenanceRequestRequest request) throws AppException;
}
