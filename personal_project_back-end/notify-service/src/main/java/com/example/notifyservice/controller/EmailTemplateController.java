package com.example.notifyservice.controller;

import com.example.common.service.entities.EmailTemplateEntity;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.notifyservice.services.EmailTemplateService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email-templates")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailTemplateController {

    EmailTemplateService emailTemplateService;

    @GetMapping
    public Page<EmailTemplateEntity> getAllWithPaging(PagingQueryConditionRequest pagingQueryConditionRequest) {
        return emailTemplateService.getAllWithPagingUsingJpa(pagingQueryConditionRequest);
    }

    @GetMapping("/:id")
    public EmailTemplateEntity getById(Long id) throws Exception {
        return emailTemplateService.getById(id);
    }

    @DeleteMapping("/:id")
    public void deleteById(Long id) {
        emailTemplateService.deletedById(id);
    }

    @PostMapping
    public EmailTemplateEntity save(@RequestBody EmailTemplateEntity emailTemplateEntity) {
        return emailTemplateService.save(emailTemplateEntity);
    }

}
