package com.example.mainservice.repositories;

import com.example.common.service.entities.UserEntity;
import com.example.common.service.models.UserModel;
import com.example.common.service.repositories.jpas.JpaCommonRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaCommonRepository<UserEntity, Long>, UserRepositoryNative {
    boolean existsByUsernameOrCccd(String username, String cccd);
    boolean existsByIdNotAndCccd(Long id, String cccd);
    List<UserEntity> findByIdIn(List<Long> ids);
    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);
}
