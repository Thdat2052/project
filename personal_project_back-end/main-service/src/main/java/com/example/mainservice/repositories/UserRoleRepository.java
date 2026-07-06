package com.example.mainservice.repositories;

import com.example.common.service.entities.UserRoleEntity;
import com.example.common.service.repositories.jpas.JpaCommonRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaCommonRepository<UserRoleEntity, Long> {

    List<UserRoleEntity> findAllByUserId(Long userId);
    void deleteByUserIdIn(List<Long> userIds);
}
