package com.example.mainservice.services;

import com.example.common.service.entities.MaintenanceFeeEntity;
import com.example.common.service.entities.MaintenanceRequestEntity;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.models.MaintenanceRequestModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.services.CommonService;
import com.example.mainservice.models.requests.MaintenanceRequestRequest;
import com.example.mainservice.models.responses.MaintenanceRequestResponse;
import org.springframework.data.domain.Page;

public interface MaintenanceFeeService extends CommonService<MaintenanceFeeEntity, Long> {
}
