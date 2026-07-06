package com.example.mainservice.repositories;

import com.example.common.service.entities.ContractEntity;
import com.example.common.service.enums.ContractStatusEnum;
import com.example.common.service.models.responses.ContractResponse;
import com.example.common.service.repositories.jpas.JpaCommonRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaCommonRepository<ContractEntity, Long>, ContractRepositoryNative {
    boolean existsByRoomIdAndStatus(Long roomId, ContractStatusEnum status);
    boolean existsByIdNotAndRoomIdAndStatus(Long id, Long roomId, ContractStatusEnum status);
    List<ContractEntity> findByUserId(Long userId);
    List<ContractEntity> findByIdIn(List<Long> ids);
    ContractEntity findByRoomId(Long roomId);
    ContractEntity findByRoomIdAndStatus(Long roomId, ContractStatusEnum status);
}
