package com.example.mainservice.repositories;

import com.example.common.service.entities.RoomEntity;
import com.example.common.service.enums.RoomStatusEnum;
import com.example.common.service.repositories.jpas.JpaCommonRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaCommonRepository<RoomEntity, Long>, RoomRepositoryNative {
    boolean existsByNumber(String number);
    boolean existsByIdNotAndNumber(Long id, String number);
    List<RoomEntity> findByIdInAndStatus(List<Long> ids, RoomStatusEnum statusEnum);
    List<RoomEntity> findAllByStatus(RoomStatusEnum statusEnum);
}
