package com.example.mainservice.controllers;

import com.example.common.service.controllers.BaseController;
import com.example.common.service.entities.UserEntity;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.internals.apis.AuthApis;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.models.UserModel;
import com.example.common.service.utils.UrlUtils;
import com.example.mainservice.services.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RequestMapping(UrlUtils.USER_URL)
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController extends BaseController<UserEntity, Long, UserService> {
    protected UserController(UserService service, AuthApis authApis) {
        super(service, authApis);
    }

    @GetMapping("paging")
    public Page<UserModel> findAllWithPaging(@ModelAttribute PagingQueryConditionRequest request) {
        return getService().findAllWithPagingUsingJpa(request);
    }
    @PostMapping
    public UserModel save(@RequestBody UserModel model) throws Exception {
        return getService().createOrUpdate(model);
    }
    @GetMapping("me")
    public UserModel findMe() throws AppException {
        return getService().findMe();
    }
    @PutMapping("me")
    public UserModel updateMe(@RequestBody UserModel model) throws AppException {
        return getService().updateMe(model);
    }

    @GetMapping("reset-password")
    public void resetPassword(@RequestParam String username) throws AppException {
        getService().resetPassword(username);
    }
}
