package com.example.notifyservice.services.impl;

import com.example.common.service.entities.EmailConfigEntity;
import com.example.common.service.enums.EmailSecurityEnum;
import com.example.common.service.enums.ErrorCodeEnum;
import com.example.common.service.exceptions.AppException;
import com.example.notifyservice.constants.EmailConstant;
import com.example.notifyservice.services.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;


    public void sendSimpleMessage(EmailConfigEntity emailConfig) {
        JavaMailSender mailSender = configureEmail(emailConfig);
        try {
            mailSender.send(mimeMessage -> {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
                message.setFrom(emailConfig.getUsername());
                message.setTo(emailConfig.getEmail());
                message.setSubject(EmailConstant.SUBJECT_EMAIL_TEST);
                message.setText(EmailConstant.BODY_EMAIL_TEST);
            });
        } catch (MailException e) {
            throw new AppException(ErrorCodeEnum.EMAIL_SEND.getMessage());
        }
    }


    @Override
    public void checkConnection(EmailConfigEntity config) {
        JavaMailSender mailSender = configureEmail(config);
        sendSimpleMessage(config);
    }

    @Override
    public void sendMessagesWithHtmlAndAttachment(EmailConfigEntity config, List<String> to, List<String> cc,
                                                  List<String> bcc, String subject, String htmlContent, String path) {
        JavaMailSender mailSender = configureEmail(config);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, EmailConstant.VALUE_UTF_8);
            helper.setFrom(config.getUsername());

            if (to != null && !to.isEmpty()) {
                helper.setTo(to.toArray(new String[0]));
            }
            if (cc != null && !cc.isEmpty()) {
                helper.setCc(cc.toArray(new String[0]));
            }
            if (bcc != null && !bcc.isEmpty()) {
                helper.setBcc(bcc.toArray(new String[0]));
            }
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            if (path != null && !path.isEmpty()) {
                File file = new File(path);
                if (file.exists()) {
                    helper.addAttachment(file.getName(), file);
                } else {
                    throw new AppException(ErrorCodeEnum.EMAIL_ATTACHMENT_NOT_FOUND.getMessage());
                }
            }
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new AppException(ErrorCodeEnum.EMAIL_SEND.getMessage());
        }
    }


    private JavaMailSender configureEmail(EmailConfigEntity config) {
        // Cấu hình SMTP server
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(config.getHost());
        mailSender.setPort(config.getPort());
        mailSender.setUsername(config.getUsername());
        mailSender.setPassword(config.getPassword());

        Properties properties = mailSender.getJavaMailProperties();
        properties.put(EmailConstant.KEY_PROTOCOL, EmailConstant.VALUE_SMTP);
        properties.put(EmailConstant.KEY_AUTH, EmailConstant.VALUE_TRUE);
        properties.put(EmailConstant.KEY_STARTTLS, EmailConstant.VALUE_TRUE);
        properties.put(EmailConstant.KEY_DEBUG, EmailConstant.VALUE_TRUE);

        if (config.getSecurity().equals(EmailSecurityEnum.SSL_TLS)) {
            properties.put(EmailConstant.KEY_SECURE, EmailConstant.VALUE_TRUE);
        }
        return mailSender;
    }
}
