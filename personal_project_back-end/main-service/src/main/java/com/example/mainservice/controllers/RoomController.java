package com.example.mainservice.controllers;

import com.example.common.service.controllers.BaseController;
import com.example.common.service.entities.RoomEntity;
import com.example.common.service.enums.InvoiceStatusEnum;
import com.example.common.service.enums.RoomStatusEnum;
import com.example.common.service.internals.apis.AuthApis;
import com.example.common.service.models.RoomModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.utils.UrlUtils;
import com.example.mainservice.models.requests.RoomConditionRequest;
import com.example.mainservice.models.responses.RoomContractResponse;
import com.example.mainservice.models.responses.UnpaidCustomerResponse;
import com.example.mainservice.services.RoomService;
import com.example.mainservice.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UrlUtils.ROOM_URL)
public class RoomController extends BaseController<RoomEntity, Long, RoomService> {
    @Autowired
    private UserService userService;
    protected RoomController(RoomService service, AuthApis authApis) {
        super(service, authApis);
    }

    @GetMapping("paging")
    public ResponseEntity<Page<RoomModel>> findAllWithPaging(@ModelAttribute RoomConditionRequest request) {
        var datas = getService().findAllWithPaging(request);
        return ResponseEntity.ok(datas);
    }

    // Danh sách phòng còn nợ chưa trả tiền phòng
    @GetMapping("/users/unpaid")
    public ResponseEntity<List<UnpaidCustomerResponse>> getUnpaidCustomers(@RequestParam("status") InvoiceStatusEnum status) {
        return ResponseEntity.ok(getService().getUnpaidCustomers(status));
    }

    // Danh sách phòng sắp hết hạns
    @GetMapping("/active-next-days")
    public ResponseEntity<List<RoomContractResponse>> getActiveContractsInNextDays(@RequestParam("days") int days) {
        return  ResponseEntity.ok(getService().findActiveContractsInNextWeek(days));
    }

    @PostMapping
    public ResponseEntity<RoomModel> createOrUpdate(@Valid @RequestBody RoomModel model) throws Exception {
        return  ResponseEntity.ok(getService().createOrUpdate(model));
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteByIdIn(@RequestParam("ids") List<Long> ids) {
        getService().deleteByIdIn(ids);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status")
    public ResponseEntity<List<RoomEntity>> findAllByStatus(@RequestParam("status") RoomStatusEnum statusEnum) {
        var datas = getService().findAllByStatus(statusEnum);
        return ResponseEntity.ok(datas);
    }


}
