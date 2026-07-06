package com.example.notifyservice.repositories;

import com.example.common.service.entities.EmailTemplateEntity;
import com.example.common.service.repositories.jpas.JpaCommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailTemplateRepository extends JpaCommonRepository<EmailTemplateEntity, Long>, EmailTemplateRepositoryCustom {
    boolean existsByName(String name);

    boolean existsByIdNotAndName(Long id, String name);

}
