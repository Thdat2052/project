package com.example.mainservice.controllers;

import com.example.common.service.controllers.BaseController;
import com.example.common.service.entities.ContractEntity;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.internals.apis.AuthApis;
import com.example.common.service.models.ContractModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.models.requests.ContractInformationRequest;
import com.example.common.service.models.responses.ContractResponse;
import com.example.common.service.utils.AuthUtils;
import com.example.common.service.utils.UrlUtils;
import com.example.mainservice.services.ContractService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping(UrlUtils.CONTRACT_URL)
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContractController extends BaseController<ContractEntity, Long, ContractService> {
    protected ContractController(ContractService service, AuthApis authApis) {
        super(service, authApis);
    }
    @GetMapping("paging")
    public Page<ContractModel> findAllWithPaging(@ModelAttribute PagingQueryConditionRequest request) {
        var data = getService().findAllWithPaging(request);
        return data;
    }
    @PostMapping()
    public ResponseEntity<ContractModel> createContract(HttpServletRequest request,
                                                        @Valid @RequestBody ContractInformationRequest contractInformationRequest)
            throws AppException, IOException, InterruptedException {
        String token = AuthUtils.getTokenFromHeader(request);
        return ResponseEntity.ok(getService().createOrUpdate(contractInformationRequest,
                AuthUtils.getHeaderDefaultWithToken(token)));
    }
    @GetMapping("me")
    public List<ContractResponse> findMyContracts() throws AppException {
        return getService().findMyContract();
    }
    @GetMapping("detail/rooms/{roomId}")
    public ResponseEntity<ContractResponse> findContractByRoomId(@PathVariable Long roomId) throws AppException {
        var entity = getService().findByRoomIdAndStatus(roomId);
        return ResponseEntity.ok(getService().findContractById(entity.getId()));
    }
    @GetMapping("detail/{id}")
    public ResponseEntity<ContractResponse> findContractById(@PathVariable("id") Long id) throws AppException {
        return ResponseEntity.ok(getService().findContractById(id));
    }
    // thêm người vào hợp đồng
    @PostMapping("{id}/users")
    public ResponseEntity<ContractResponse> addUserToContract(
            @PathVariable("id") Long id,
            @RequestBody List<Long> userIds
    ) throws AppException {
        return ResponseEntity.ok(getService().addUserToContract(id, userIds));
    }
}
