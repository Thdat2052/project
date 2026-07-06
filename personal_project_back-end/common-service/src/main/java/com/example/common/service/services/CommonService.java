package com.example.common.service.services;

import com.example.common.service.exceptions.AppException;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommonService<E,ID> {
    List<E> getAll();

    E getById(ID id) throws AppException;

    E save(E entity);

    List<E> save(List<E> entity);

    void deletedById(ID id);

    boolean existsById (ID id);

    void deletedByIdIn(List<ID> ids);

    Page<E> getAllWithPagingUsingJpa(PagingQueryConditionRequest pagingQueryConditionRequest);
}
