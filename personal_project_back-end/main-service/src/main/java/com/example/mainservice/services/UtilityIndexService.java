package com.example.mainservice.services;

import com.example.common.service.entities.UtilityIndexEntity;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.models.UtilityIndexModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.services.CommonService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UtilityIndexService extends CommonService<UtilityIndexEntity, Long> {
    UtilityIndexModel createOrUpdate(UtilityIndexModel utilityIndexModel) throws AppException;
    UtilityIndexEntity findByRoomIdAndMonthMeasureAndYearMeasure(Long roomId, Integer month, Integer year);
    List<UtilityIndexModel> findByContractId(Long contractId) throws AppException;
    List<UtilityIndexModel> findAllWithRoomIsRented();
    Page<UtilityIndexModel> findAllWithPaging(PagingQueryConditionRequest request) throws AppException;
}
