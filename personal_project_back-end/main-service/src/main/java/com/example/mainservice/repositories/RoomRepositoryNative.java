package com.example.mainservice.repositories;

import com.example.common.service.enums.InvoiceStatusEnum;
import com.example.common.service.enums.RoomStatusEnum;
import com.example.common.service.models.RoomModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.mainservice.models.requests.RoomConditionRequest;
import com.example.mainservice.models.responses.RoomContractResponse;
import com.example.mainservice.models.responses.UnpaidCustomerResponse;
import org.springframework.data.domain.Page;

import java.util.List;


public interface RoomRepositoryNative {
    List<UnpaidCustomerResponse> getUnpaidCustomers(InvoiceStatusEnum status);
    List<RoomContractResponse> findActiveContractsInNextWeek(int numberDay);
    Page<RoomModel> findAllByPaging(RoomConditionRequest pageRequest);
}
