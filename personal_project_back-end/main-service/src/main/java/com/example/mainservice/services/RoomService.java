package com.example.mainservice.services;

import com.example.common.service.entities.RoomEntity;
import com.example.common.service.enums.InvoiceStatusEnum;
import com.example.common.service.enums.RoomStatusEnum;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.models.RoomModel;
import com.example.common.service.services.CommonService;
import com.example.mainservice.models.requests.RoomConditionRequest;
import com.example.mainservice.models.responses.RoomContractResponse;
import com.example.mainservice.models.responses.UnpaidCustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface RoomService extends CommonService<RoomEntity, Long> {
    RoomModel createOrUpdate(RoomModel model) throws Exception;
    boolean existsByIdAndStatus(Long id, RoomStatusEnum status);
    List<UnpaidCustomerResponse> getUnpaidCustomers(InvoiceStatusEnum status);
    List<RoomContractResponse> findActiveContractsInNextWeek(int numberDay);
    Page<RoomModel>findAllWithPaging(RoomConditionRequest roomConditionRequest);
    void deleteByIdIn(List<Long> ids);
    List<RoomEntity> findAllByStatus(RoomStatusEnum statusEnum);
}
