package com.example.mainservice.controllers;

import com.example.common.service.controllers.BaseController;
import com.example.common.service.entities.MaintenanceRequestEntity;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.internals.apis.AuthApis;
import com.example.common.service.models.MaintenanceRequestModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.utils.UrlUtils;
import com.example.mainservice.models.requests.MaintenanceRequestRequest;
import com.example.mainservice.models.responses.MaintenanceRequestResponse;
import com.example.mainservice.services.MaintenanceRequestService;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RequestMapping(UrlUtils.MAINTENANCE_REQUEST_URL)
@RestController
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class MaintenanceRequestController  extends BaseController<MaintenanceRequestEntity, Long, MaintenanceRequestService> {

    protected MaintenanceRequestController(MaintenanceRequestService service, AuthApis authApis) {
        super(service, authApis);
    }
    @PostMapping
    public MaintenanceRequestModel create(@RequestBody MaintenanceRequestModel model) throws AppException {
        return getService().create(model);
    }
    @GetMapping("/paging")
    public Page<MaintenanceRequestResponse> findAllWithPaging(@ModelAttribute PagingQueryConditionRequest request) {
        var data = getService().findAllWithPaging(request);
        return data;
    }
    @PutMapping("/{id}")
    public MaintenanceRequestModel update(@PathVariable Long id, @RequestBody MaintenanceRequestRequest model) throws AppException {
        return getService().update(id, model);
    }
}
