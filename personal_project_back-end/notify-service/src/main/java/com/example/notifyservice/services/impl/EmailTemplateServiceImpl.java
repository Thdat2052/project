package com.example.notifyservice.services.impl;

import com.example.common.service.entities.EmailTemplateEntity;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.notifyservice.repositories.EmailTemplateRepository;
import com.example.notifyservice.services.EmailTemplateService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailTemplateServiceImpl implements EmailTemplateService {
    EmailTemplateRepository emailTemplateRepository;

    @Override
    public List<EmailTemplateEntity> getAll() {
        return List.of();
    }

    @Override
    public EmailTemplateEntity getById(Long aLong)  {
        return emailTemplateRepository.findById(aLong).orElseThrow(() -> new AppException("Email template not found"));
    }

    @Override
    public EmailTemplateEntity save(EmailTemplateEntity entity) {
        // validate entity for create or update email template
        if (entity.getId() == null && emailTemplateRepository.existsByName(entity.getName())) {
            throw new AppException("Email template with name " + entity.getName() + " already exists");
        } else if (emailTemplateRepository.existsByIdNotAndName(entity.getId(), entity.getName())) {
            throw new AppException("Email template with name " + entity.getName() + " already exists");
        }

        return emailTemplateRepository.save(entity);
    }

    @Override
    public List<EmailTemplateEntity> save(List<EmailTemplateEntity> entity) {
        return List.of();
    }

    @Override
    public void deletedById(Long aLong) {
        emailTemplateRepository.deleteById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return emailTemplateRepository.existsById(aLong);
    }

    @Override
    public void deletedByIdIn(List<Long> longs) {
        emailTemplateRepository.deleteByIdIn(longs);
    }

    @Override
    public Page<EmailTemplateEntity> getAllWithPagingUsingJpa(PagingQueryConditionRequest pagingQueryConditionRequest) {
        return emailTemplateRepository.findAllByPaging(pagingQueryConditionRequest);
    }
}
