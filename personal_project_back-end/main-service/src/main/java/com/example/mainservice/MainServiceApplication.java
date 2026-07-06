package com.example.mainservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
@EntityScan(basePackages = "com.example.common.service.entities")
@ComponentScan(basePackages = {"com.example.common.service.components", "com.example.common.service.configs"
        ,"com.example.mainservice" , "com.example.common.service.internals",
        "com.example.common.service.exceptions", "com.example.common.service.securities"})
public class MainServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainServiceApplication.class, args);
    }

}
