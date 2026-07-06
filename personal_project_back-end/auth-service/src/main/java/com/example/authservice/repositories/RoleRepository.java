package com.example.authservice.repositories;


import com.example.common.service.entities.RoleEntity;
import com.example.common.service.enums.RoleEnum;
import com.example.common.service.repositories.jpas.JpaCommonRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaCommonRepository<RoleEntity, Long> {

    List<RoleEntity> findAllByIdIn(List<Long> roleIds);
    Optional<RoleEntity> findByName(RoleEnum name);
}
