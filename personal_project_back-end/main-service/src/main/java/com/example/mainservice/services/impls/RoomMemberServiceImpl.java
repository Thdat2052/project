package com.example.mainservice.services.impls;


import com.example.common.service.entities.RoomMemberEntity;
import com.example.common.service.models.UserModel;
import com.example.common.service.services.impls.CommonServiceImpl;
import com.example.mainservice.repositories.RoomMemberRepository;
import com.example.mainservice.services.RoomMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomMemberServiceImpl extends CommonServiceImpl<RoomMemberEntity,Long, RoomMemberRepository> implements RoomMemberService {

    @Autowired
    private RoomMemberRepository repository;

    protected RoomMemberServiceImpl(RoomMemberRepository repo) {
        super(repo);
    }

    @Override
    @Transactional
    public List<RoomMemberEntity> create(Long contractId, List<UserModel> users){
        List<RoomMemberEntity> entities = new ArrayList<>();
        for(UserModel user : users){
            RoomMemberEntity roomMemberEntity= new RoomMemberEntity();
            roomMemberEntity.setContractId(contractId);
            roomMemberEntity.setUserId(user.getId());
            entities.add(roomMemberEntity);
        }
        return repository.saveAll(entities);
    }
    @Override
    public List<RoomMemberEntity> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }
    @Override
    public List<UserModel> findByContractId(Long contractId) {
        return repository.findByContractId(contractId);
    }

    @Override
    public void deleteByContractId(Long contractId) {
        repository.deleteByContractId(contractId);
    }

}
