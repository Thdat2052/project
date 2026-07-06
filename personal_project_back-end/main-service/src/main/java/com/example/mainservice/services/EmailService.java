package com.example.mainservice.services;

import com.example.common.service.entities.EmailConfigEntity;

import java.util.List;

public interface EmailService {
    void sendEmailForgotPassword(String to,String password);
}
