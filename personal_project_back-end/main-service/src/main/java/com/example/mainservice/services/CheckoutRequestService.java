package com.example.mainservice.services;

import com.example.common.service.entities.CheckoutRequestEntity;
import com.example.common.service.enums.CheckoutRequestStatusEnum;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.models.CheckoutRequestModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.services.CommonService;
import org.springframework.data.domain.Page;

public interface CheckoutRequestService extends CommonService<CheckoutRequestEntity, Long> {
    CheckoutRequestModel create(CheckoutRequestModel request) throws AppException;
    CheckoutRequestModel approve(Long id, CheckoutRequestStatusEnum status) throws AppException;
    Page<CheckoutRequestModel> findAllWithPaging(PagingQueryConditionRequest request) throws AppException;
}
