package com.example.notifyservice.controller;

import com.example.common.service.exceptions.AppException;
import com.example.common.service.utils.UrlUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(UrlUtils.USER_URL)
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    private UserService userService;

    @GetMapping("reset-password")
    public void resetPassword(@RequestParam String email) throws AppException {
        userService.resetPassword(email);
    }
}
