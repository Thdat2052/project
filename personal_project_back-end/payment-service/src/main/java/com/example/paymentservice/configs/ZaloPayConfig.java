package com.example.paymentservice.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "zalopay")
public class ZaloPayConfig {
    private String appId;          // zalopay.app-id
    private String key1;           // zalopay.key1
    private String key2;           // zalopay.key2
    private String endpoint;       // zalopay.endpoint
    private String orderStatusUrl; // zalopay.order-status-url
    private String returnUrl;      // zalopay.return-url
    private String notifyUrl;      // zalopay.notify-url

}