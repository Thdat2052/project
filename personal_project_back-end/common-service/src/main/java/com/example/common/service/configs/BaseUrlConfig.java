package com.example.common.service.configs;

import com.example.common.service.enums.ServiceNameEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class BaseUrlConfig {

    private final Environment environment;

    public BaseUrlConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public Map<ServiceNameEnum, String> serviceUrlMap() {
        Map<ServiceNameEnum, String> serviceUrlMap = new HashMap<>();

        for (ServiceNameEnum service : ServiceNameEnum.values()) {
            String serviceKey = service.getKey();
            String propertyKey = "app.service." + serviceKey + ".url";
            String url = environment.getProperty(propertyKey);

            serviceUrlMap.put(service, url);
        }

        return serviceUrlMap;
    }

}
