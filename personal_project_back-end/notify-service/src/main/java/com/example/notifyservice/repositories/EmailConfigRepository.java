package com.example.notifyservice.repositories;

import com.example.common.service.entities.EmailConfigEntity;
import com.example.common.service.repositories.jpas.JpaCommonRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface EmailConfigRepository extends JpaCommonRepository<EmailConfigEntity, Long>, EmailConfigRepositoryCustom {
    // exists by host and protocol
    boolean existsByHostAndProtocol(String host, String protocol);

    // exists by id not and host and protocol
    boolean existsByIdNotAndHostAndProtocol(Long id, String host, String protocol);
}
