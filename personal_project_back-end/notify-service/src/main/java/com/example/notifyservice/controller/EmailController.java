package com.example.notifyservice.controller;

import com.example.notifyservice.services.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {
    EmailService emailService;

    @GetMapping("/send")
    public boolean sendEmail() {
        emailService.sendMessagesWithHtmlAndAttachment(null, null, null, null, null, null, null);
        return true;
    }
}
