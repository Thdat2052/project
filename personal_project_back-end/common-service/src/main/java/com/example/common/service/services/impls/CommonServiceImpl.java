package com.example.common.service.services.impls;


import com.example.common.service.exceptions.AppException;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.repositories.jpas.JpaCommonRepository;
import com.example.common.service.services.CommonService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class CommonServiceImpl<E,ID, R extends JpaCommonRepository<E,ID>> implements CommonService<E,ID> {

    R repo;
    protected CommonServiceImpl(R repo) {
        this.repo = repo;
    }

    @Override
    public Page<E> getAllWithPagingUsingJpa(PagingQueryConditionRequest pagingQueryConditionRequest) {
        Pageable pageable = PageRequest.of(pagingQueryConditionRequest.getPageCurrent(), pagingQueryConditionRequest.getPageSize());
        return repo.findAll(pageable);
    }

    @Override
    public List<E> getAll() {
        return repo.findAll();
    }

    @Override
    public E getById(ID id) throws AppException {
        Optional<E> entityOptional = repo.findById(id);
        if(!entityOptional.isPresent()) throw new AppException("Entity not found");
        return entityOptional.get();
    }

    @Override
    public E save(E entity) {
        return repo.save(entity);
    }

    @Override
    public List<E> save(List<E> entity) {
        return repo.saveAll(entity);
    }

    @Override
    public void deletedById(ID id) {
      repo.deleteById(id);
    }

    @Override
    public boolean existsById(ID id) {
        return repo.existsById(id);
    }

    @Override
    public void deletedByIdIn(List<ID> ids) {
     repo.deleteByIdIn(ids);
    }

}
