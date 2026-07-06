package com.example.notifyservice.repositories;

import com.example.common.service.entities.EmailTemplateEntity;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import org.springframework.data.domain.Page;

public interface EmailTemplateRepositoryCustom {
    Page<EmailTemplateEntity> findAllByPaging(PagingQueryConditionRequest pageRequest);
}
