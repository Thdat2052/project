package com.example.common.service.mappers;

import com.example.common.service.models.requests.EmailConfigRequest;
import com.example.common.service.models.responses.EmailConfigResponse;
import com.example.common.service.entities.EmailConfigEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EmailConfigMapper {

    EmailConfigMapper INSTANCE = Mappers.getMapper(EmailConfigMapper.class);

     EmailConfigEntity toEntity(EmailConfigRequest request);

     EmailConfigResponse toResponse(EmailConfigEntity entity);

    default Page<EmailConfigResponse> toPageResponse(Page<EmailConfigEntity> entityPage) {
        List<EmailConfigResponse> responses = entityPage.getContent().stream()
                .map(this::toResponse)
                .toList();
        return new PageImpl<>(responses, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
