
package com.example.common.service.controllers;

import com.example.common.service.enums.RoleEnum;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.internals.apis.AuthApis;
import com.example.common.service.models.ErrorModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.models.requests.TokenValidationRequest;
import com.example.common.service.services.CommonService;
import com.example.common.service.utils.AuthUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

public abstract class BaseController<E, ID, S extends CommonService<E, ID>> {
    private S service;
    private AuthApis authApis;

    protected BaseController(S service, AuthApis authApis){
        this.service = service;
        this.authApis = authApis;
    }

    protected AuthApis getAuthApis() {
        return authApis;
    }

    protected S getService(){
        return service;
    }


    @GetMapping
    public List<E> getAll() {
        return service.getAll();
    }


    @GetMapping("/page")
    public Page<E> getAllWithPagingUsingJpa(@RequestParam(name = "pageSize") int pageSize,
                                            @RequestParam(name = "pageCurrent") int pageCurrent) {
        PagingQueryConditionRequest pagingQueryConditionRequest = new PagingQueryConditionRequest(pageSize, pageCurrent);
        return service.getAllWithPagingUsingJpa(pagingQueryConditionRequest);
    }

    @GetMapping("/{id}")
    public E getById(@PathVariable("id") ID id) throws Exception {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") ID id) {
        service.deletedById(id);
    }

    protected Boolean validatePermission(HttpServletRequest request, List<RoleEnum> roleRequired) throws AppException, IOException, InterruptedException {
        TokenValidationRequest tokenValidationRequest = new TokenValidationRequest();
        String token = AuthUtils.getTokenFromHeader(request);
        tokenValidationRequest.setToken(token);
        tokenValidationRequest.setRoleNeeded(roleRequired);
        var response = authApis.validateToken(tokenValidationRequest);
        if (response == null || response.getData() == null || !response.getData()) {
            throw new AppException(ErrorModel.of(HttpStatus.FORBIDDEN.toString(), "You don't have permission"));
        }
        return response.getData();
    }

}
