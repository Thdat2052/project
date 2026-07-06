package com.example.paymentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.common.service.entities")
@ComponentScan(basePackages = {"com.example.common.service.components", "com.example.common.service.configs"
        ,"com.example.paymentservice" , "com.example.common.service.internals", "com.example.common.service.exceptions"})
public class PaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }

}
