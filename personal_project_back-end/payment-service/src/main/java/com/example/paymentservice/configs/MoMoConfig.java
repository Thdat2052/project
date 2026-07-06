package com.example.paymentservice.configs;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "momo")
public class MoMoConfig {
    private String partnerCode;  // momo.partner-code
    private String accessKey;    // momo.access-key
    private String secretKey;    // momo.secret-key
    private String redirectUrl;  // momo.redirect-url
    private String ipnUrl;       // momo.ipn-url
    private String requestType;  // momo.request-type
}