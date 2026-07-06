package com.example.paymentservice.configs;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "paypal")
public class PayPalConfig {
    private String clientId;    // paypal.client-id
    private String clientSecret; // paypal.client-secret
    private String mode;        // paypal.mode
    private String cancelUrl;   // paypal.cancel-url
    private String successUrl;  // paypal.success-url
}