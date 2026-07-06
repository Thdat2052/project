package com.example.mainservice.repositories;

import com.example.common.service.models.CheckoutRequestModel;
import com.example.common.service.models.ContractModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.models.responses.ContractResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CheckoutRequestRepositoryNative {
    Page<CheckoutRequestModel> findAllWithPaging(PagingQueryConditionRequest pageRequest);
}
