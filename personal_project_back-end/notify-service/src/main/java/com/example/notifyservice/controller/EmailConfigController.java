package com.example.notifyservice.controller;

import com.example.common.service.models.requests.EmailConfigRequest;
import com.example.common.service.models.responses.EmailConfigResponse;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.notifyservice.services.EmailConfigService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/email-config")
public class EmailConfigController {
    EmailConfigService emailConfigService;

    @GetMapping
    public Page<EmailConfigResponse> getAllWithPaging(PagingQueryConditionRequest pagingQueryConditionRequest) {
        return emailConfigService.getAllWithPagingUsingJpa(pagingQueryConditionRequest);
    }

    @GetMapping("/:id")
    public EmailConfigResponse getById(Long id) throws Exception {
        return emailConfigService.getById(id);
    }

    @DeleteMapping("/:id")
    public void deleteById(Long id) {
        emailConfigService.deletedById(id);
    }

    @PostMapping
    public EmailConfigResponse save(@RequestBody EmailConfigRequest emailConfigRequest) throws Exception {
        return emailConfigService.save(emailConfigRequest);
    }
}
