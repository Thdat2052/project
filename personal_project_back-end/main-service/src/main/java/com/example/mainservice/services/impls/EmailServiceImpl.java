package com.example.mainservice.services.impls;

import com.example.mainservice.constants.EmailConstant;
import com.example.mainservice.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmailForgotPassword(String to, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(EmailConstant.SUBJECT_EMAIL_FORGOT_PASSWORD);
        message.setText(EmailConstant.BODY_EMAIL_FORGOT_PASSWORD + "\n" + password);
        mailSender.send(message);
    }


}
