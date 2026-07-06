package com.example.mainservice.repositories;

import com.example.common.service.entities.ServiceEntity;
import com.example.common.service.repositories.jpas.JpaCommonRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaCommonRepository<ServiceEntity, Long> {
    long countByIdIn(List<Long> ids);
    List<ServiceEntity> findByIdIn(List<Long> ids);
}
