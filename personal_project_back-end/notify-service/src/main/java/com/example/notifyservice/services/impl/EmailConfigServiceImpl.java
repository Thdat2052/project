package com.example.notifyservice.services.impl;


import com.example.common.service.mappers.EmailConfigMapper;
import com.example.common.service.models.requests.EmailConfigRequest;
import com.example.common.service.models.responses.EmailConfigResponse;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.utils.EncryptionUtils;
import com.example.notifyservice.repositories.EmailConfigRepository;
import com.example.notifyservice.services.EmailConfigService;
import com.example.notifyservice.services.EmailService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailConfigServiceImpl implements EmailConfigService {
    @Autowired
    EmailConfigRepository emailConfigRepository;

    @Autowired
    EmailService emailService;

    @Value("${email-config.password-encryption-key}")
    private String encryptionKey;

    @Override
    public EmailConfigResponse save(EmailConfigRequest emailConfigRequest) throws Exception {
        if (emailConfigRequest.getId() == null) {
            boolean exists = emailConfigRepository.existsByHostAndProtocol(
                    emailConfigRequest.getHost(),
                    emailConfigRequest.getProtocol().name());
            if (exists) {
                throw new AppException("Email config already exists");
            }
        } else {
            boolean exists = emailConfigRepository.existsByIdNotAndHostAndProtocol(
                    emailConfigRequest.getId(),
                    emailConfigRequest.getHost(),
                    emailConfigRequest.getProtocol().name());
            if (exists) {
                throw new AppException("Email config already exists");
            }
        }
        var emailConfigEntity = EmailConfigMapper.INSTANCE.toEntity(emailConfigRequest);
        emailService.checkConnection(emailConfigEntity);
        // password encryption
        emailConfigEntity.setPassword(EncryptionUtils.encrypt(emailConfigRequest.getPassword(), encryptionKey));
        return EmailConfigMapper.INSTANCE.toResponse(emailConfigRepository.save(emailConfigEntity));
    }

    @Override
    public Page<EmailConfigResponse> getAllWithPagingUsingJpa(PagingQueryConditionRequest pagingQueryConditionRequest) {
        return EmailConfigMapper.INSTANCE.toPageResponse(emailConfigRepository.findAllByPaging(pagingQueryConditionRequest));
    }

    @Override
    public void deletedById(Long id) {
        emailConfigRepository.deleteById(id);
    }

    @Override
    public EmailConfigResponse getById(Long id) throws AppException {
        return EmailConfigMapper.INSTANCE.toResponse(emailConfigRepository.findById(id)
                .orElseThrow(() -> new AppException("Email config not found")));
    }


}
