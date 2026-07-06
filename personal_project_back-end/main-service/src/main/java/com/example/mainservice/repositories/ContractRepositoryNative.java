package com.example.mainservice.repositories;

import com.example.common.service.entities.ContractEntity;
import com.example.common.service.models.ContractModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.models.responses.ContractResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContractRepositoryNative {
    Page<ContractModel> findAllWithPaging(PagingQueryConditionRequest pageRequest);
    ContractResponse findContractById(Long id);
    List<ContractResponse> findByUserIdIn(List<Long> ids);
}
