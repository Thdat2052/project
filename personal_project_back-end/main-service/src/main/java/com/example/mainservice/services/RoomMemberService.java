package com.example.mainservice.services;

import com.example.common.service.entities.RoomMemberEntity;
import com.example.common.service.models.UserModel;
import com.example.common.service.services.CommonService;

import java.util.List;


public interface RoomMemberService extends CommonService<RoomMemberEntity, Long> {
    List<RoomMemberEntity> create(Long contractId, List<UserModel> users);
    List<RoomMemberEntity> findByUserId(Long userId);
    List<UserModel> findByContractId(Long contractId);
    void deleteByContractId(Long contractId);

}
