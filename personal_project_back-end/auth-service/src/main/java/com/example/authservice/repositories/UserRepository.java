package com.example.authservice.repositories;

import com.example.authservice.repositories.natives.UserNativeRepository;
import com.example.common.service.entities.UserEntity;
import com.example.common.service.repositories.jpas.JpaCommonRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaCommonRepository<UserEntity, Long>, UserNativeRepository {

    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    List<UserEntity> findByTransactionId(String transactionId);
    void deleteByTransactionId(String transactionId);
}
