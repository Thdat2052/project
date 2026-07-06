package com.example.fileservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.common.service.entities")
@ComponentScan(basePackages = {"com.example.common.service.components", "com.example.common.service.configs"
        ,"com.example.fileservice" , "com.example.common.service.internals", "com.example.common.service.exceptions"
        , "com.example.common.service.securities"})
public class FileServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileServiceApplication.class, args);
    }

}
