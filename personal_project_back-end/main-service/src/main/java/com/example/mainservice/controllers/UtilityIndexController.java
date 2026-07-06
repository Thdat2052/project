package com.example.mainservice.controllers;

import com.example.common.service.controllers.BaseController;
import com.example.common.service.entities.ServiceEntity;
import com.example.common.service.entities.UtilityIndexEntity;
import com.example.common.service.enums.RoleEnum;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.internals.apis.AuthApis;
import com.example.common.service.models.UtilityIndexModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.utils.UrlUtils;
import com.example.mainservice.services.AmenityService;
import com.example.mainservice.services.UtilityIndexService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(UrlUtils.UTILITY_INDEX_URL)
public class UtilityIndexController extends BaseController<UtilityIndexEntity, Long, UtilityIndexService> {

    protected UtilityIndexController(UtilityIndexService service, AuthApis authApis) {
        super(service, authApis);
    }
    @GetMapping("/all/room/rented")
    public ResponseEntity<List<UtilityIndexModel>> findAllWithRoomIsRented(){
        return ResponseEntity.ok(getService().findAllWithRoomIsRented());
    }

    @PostMapping("/save")
    public ResponseEntity<UtilityIndexModel> createOrUpdate(@Valid @RequestBody UtilityIndexModel utilityIndexModel)
            throws AppException {
        return ResponseEntity.ok(getService().createOrUpdate(utilityIndexModel));
    }
    @GetMapping("/{id}/contract")
    public ResponseEntity<List<UtilityIndexModel>> getByContractId(@PathVariable("id") Long id) throws AppException {
        return ResponseEntity.ok(getService().findByContractId(id));
    }
    @GetMapping("/paging")
    public ResponseEntity<Page<UtilityIndexModel>> findAllWithPaging(@ModelAttribute PagingQueryConditionRequest request) {
        return ResponseEntity.ok(getService().findAllWithPaging(request));
    }
}
