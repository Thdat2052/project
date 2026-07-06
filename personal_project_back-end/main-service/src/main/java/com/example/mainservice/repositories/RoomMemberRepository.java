package com.example.mainservice.repositories;

import com.example.common.service.entities.RoomMemberEntity;
import com.example.common.service.repositories.jpas.JpaCommonRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomMemberRepository extends JpaCommonRepository<RoomMemberEntity, Long>, RoomMemberRepositoryNative {
    List<RoomMemberEntity> findByUserId(Long userId);
    void deleteByContractId(Long contractId);
}
