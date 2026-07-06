package com.example.notifyservice.services;

import com.example.common.service.entities.EmailConfigEntity;

import java.util.List;

public interface EmailService {

    void checkConnection(EmailConfigEntity config);

    void sendMessagesWithHtmlAndAttachment(EmailConfigEntity config, List<String> to, List<String> cc, List<String> bcc,
                                           String subject, String htmlContent, String path);
}
