package com.example.mainservice.services.impls;

import com.example.common.service.entities.UserRoleEntity;
import com.example.common.service.services.impls.CommonServiceImpl;
import com.example.mainservice.repositories.UserRoleRepository;
import com.example.mainservice.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl extends CommonServiceImpl<UserRoleEntity, Long, UserRoleRepository> implements UserRoleService {

    @Autowired
    private UserRoleRepository repository;

    protected UserRoleServiceImpl(UserRoleRepository repo) {
        super(repo);
    }

    @Override
    public List<UserRoleEntity> findAllByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public void deleteByUserIdIn(List<Long> userIds) {
        repository.deleteByUserIdIn(userIds);
    }

}
