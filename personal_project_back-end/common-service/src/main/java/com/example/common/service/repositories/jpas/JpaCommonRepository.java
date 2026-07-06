package com.example.common.service.repositories.jpas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface JpaCommonRepository<T, ID> extends JpaRepository<T, ID> {
    void deleteByIdIn(List<ID> ids);
}
