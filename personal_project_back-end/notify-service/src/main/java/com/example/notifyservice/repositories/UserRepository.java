package com.example.notifyservice.repositories;

import com.example.common.service.entities.UserEntity;
import com.example.common.service.repositories.jpas.JpaCommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaCommonRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);
}
