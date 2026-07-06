package com.example.mainservice.services;

import com.example.common.service.entities.ContractEntity;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.models.ContractModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.models.requests.ContractInformationRequest;
import com.example.common.service.models.responses.ContractResponse;
import com.example.common.service.services.CommonService;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ContractService extends CommonService<ContractEntity, Long> {
    ContractModel createOrUpdate(ContractInformationRequest request, Map<String, String> header) throws AppException, IOException
            , InterruptedException;
    Page<ContractModel> findAllWithPaging(PagingQueryConditionRequest pagingQueryConditionRequest);
    List<ContractResponse> findMyContract() throws AppException;
    ContractResponse findContractById(Long id) throws AppException;
    ContractResponse addUserToContract(Long id, List<Long> userIds) throws AppException;
    ContractModel findByRoomId(Long roomId) throws AppException;
    ContractEntity findByRoomIdAndStatus(Long roomId);
}
