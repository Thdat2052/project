package com.example.mainservice.repositories;

import com.example.common.service.entities.CheckoutRequestEntity;
import com.example.common.service.enums.CheckoutRequestStatusEnum;
import com.example.common.service.repositories.jpas.JpaCommonRepository;

public interface CheckoutRequestRepository extends JpaCommonRepository<CheckoutRequestEntity, Long>,
        CheckoutRequestRepositoryNative {
    boolean existsByRoomIdAndStatus(Long roomId, CheckoutRequestStatusEnum status);
    boolean existsByIdNotAndRoomIdAndStatus(Long id, Long roomId, CheckoutRequestStatusEnum status);
}
