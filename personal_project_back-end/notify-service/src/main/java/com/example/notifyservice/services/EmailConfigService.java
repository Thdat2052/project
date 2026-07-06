package com.example.notifyservice.services;

import com.example.common.service.models.requests.EmailConfigRequest;
import com.example.common.service.models.responses.EmailConfigResponse;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import org.springframework.data.domain.Page;

public interface EmailConfigService {
    EmailConfigResponse save(EmailConfigRequest emailConfigRequest) throws Exception;

    Page<EmailConfigResponse> getAllWithPagingUsingJpa(PagingQueryConditionRequest pagingQueryConditionRequest);

    void deletedById(Long id);

    EmailConfigResponse getById(Long id) throws Exception;

}
