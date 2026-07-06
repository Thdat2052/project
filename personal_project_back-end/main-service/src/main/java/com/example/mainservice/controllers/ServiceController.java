package com.example.mainservice.controllers;

import com.example.common.service.controllers.BaseController;
import com.example.common.service.entities.ServiceEntity;
import com.example.common.service.enums.RoleEnum;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.internals.apis.AuthApis;
import com.example.common.service.models.ServiceModel;
import com.example.common.service.utils.UrlUtils;
import com.example.mainservice.services.AmenityService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(UrlUtils.SERVICE_URL)
public class ServiceController extends BaseController<ServiceEntity, Long, AmenityService> {

    protected ServiceController(AmenityService service, AuthApis authApis) {
        super(service, authApis);
    }

    @GetMapping("/test")
    public Boolean test(HttpServletRequest servletRequest) throws AppException, IOException, InterruptedException {
        return validatePermission(servletRequest, List.of(RoleEnum.ADMIN));
    }

    @PostMapping
    public ResponseEntity<ServiceModel> createOrUpdate(@RequestBody ServiceModel serviceEntity,
                                        HttpServletRequest servletRequest) throws AppException, IOException, InterruptedException {
        validatePermission(servletRequest, List.of(RoleEnum.ADMIN));

        return ResponseEntity.ok(getService().createOrUpdate(serviceEntity));
    }

}
