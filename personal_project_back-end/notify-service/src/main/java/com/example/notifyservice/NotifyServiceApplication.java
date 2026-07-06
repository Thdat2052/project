package com.example.notifyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.common.service.entities")
public class NotifyServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotifyServiceApplication.class, args);
    }
}
