package com.example.notifyservice.repositories;

import com.example.common.service.entities.EmailConfigEntity;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import org.springframework.data.domain.Page;

public interface EmailConfigRepositoryCustom {
    Page<EmailConfigEntity> findAllByPaging(PagingQueryConditionRequest pageRequest);
}
