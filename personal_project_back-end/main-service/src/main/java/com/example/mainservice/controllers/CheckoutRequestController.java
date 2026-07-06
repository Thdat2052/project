package com.example.mainservice.controllers;

import com.example.common.service.controllers.BaseController;
import com.example.common.service.entities.CheckoutRequestEntity;
import com.example.common.service.enums.CheckoutRequestStatusEnum;
import com.example.common.service.internals.apis.AuthApis;
import com.example.common.service.models.CheckoutRequestModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.utils.UrlUtils;
import com.example.mainservice.services.CheckoutRequestService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(UrlUtils.CHECKOUT_REQUEST_URL)
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CheckoutRequestController extends BaseController<CheckoutRequestEntity, Long, CheckoutRequestService> {
    protected CheckoutRequestController(CheckoutRequestService service, AuthApis authApis) {
        super(service, authApis);
    }

    @PostMapping("/create")
    public CheckoutRequestModel create(@RequestBody CheckoutRequestModel request) throws Exception {
        return getService().create(request);
    }
    @PutMapping("/{id}")
    public CheckoutRequestModel approve(@PathVariable Long id ,@RequestParam CheckoutRequestStatusEnum status) throws Exception {
        return getService().approve(id, status);
    }
    @GetMapping("paging")
    public ResponseEntity<Page<CheckoutRequestModel>> findAllWithPaging(@ModelAttribute PagingQueryConditionRequest request) {
        var data = getService().findAllWithPaging(request);
        return ResponseEntity.ok(data);
    }

}
