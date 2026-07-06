package com.example.mainservice.repositories;

import com.example.common.service.entities.UtilityIndexEntity;
import com.example.common.service.models.UtilityIndexModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.repositories.jpas.JpaCommonRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UtilityIndexRepositoryNative {
    List<UtilityIndexModel> findAllWithRoomIsRented();
    Page<UtilityIndexModel> findAllWithPaging(PagingQueryConditionRequest request);
}
