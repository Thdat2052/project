package com.example.mainservice.repositories;


import com.example.common.service.enums.ContractStatusEnum;
import com.example.common.service.enums.MaintenanceRequestStatusEnum;
import com.example.common.service.models.InvoiceModel;
import com.example.common.service.models.MaintenanceRequestModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.mainservice.models.InvoiceInformationModel;
import com.example.mainservice.models.InvoiceServiceInfoModel;
import com.example.mainservice.models.ServiceInRoomModel;
import com.example.mainservice.models.requests.MaintenanceRequestRequest;
import com.example.mainservice.models.responses.MaintenanceRequestResponse;
import com.example.mainservice.models.responses.RevenueStatResponse;
import org.springframework.data.domain.Page;

import java.util.List;


public interface MaintenanceRequestRepositoryNative {
   Page<MaintenanceRequestResponse> findAllWithPaging(PagingQueryConditionRequest pageRequest);
}