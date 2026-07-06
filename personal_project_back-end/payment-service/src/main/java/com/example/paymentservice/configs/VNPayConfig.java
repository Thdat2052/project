package com.example.paymentservice.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "vnpay")
public class VNPayConfig {
    private String payUrl;     // vnpay.pay-url
    private String returnUrl;  // vnpay.return-url
    private String tmnCode;    // vnpay.tmn-code
    private String secretKey;  // vnpay.secret-key
    private String apiUrl;     // vnpay.api-url

}