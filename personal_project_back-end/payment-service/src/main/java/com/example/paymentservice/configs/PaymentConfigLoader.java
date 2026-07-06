package com.example.paymentservice.configs;
import com.example.paymentservice.enums.PaymentMethodEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PaymentConfigLoader {

    private final PayPalConfig payPalConfig;
    private final VNPayConfig vnpayConfig;
    private final ZaloPayConfig zaloPayConfig;
    private final MoMoConfig moMoConfig;

    public PaymentConfigLoader(PayPalConfig payPalConfig, VNPayConfig vnpayConfig,
                               ZaloPayConfig zaloPayConfig, MoMoConfig moMoConfig) {
        this.payPalConfig = payPalConfig;
        this.vnpayConfig = vnpayConfig;
        this.zaloPayConfig = zaloPayConfig;
        this.moMoConfig = moMoConfig;
    }

    @Bean
    public Map<PaymentMethodEnum, Object> paymentGatewayConfigs() {
        Map<PaymentMethodEnum, Object> configMap = new HashMap<>();
        configMap.put(PaymentMethodEnum.PAYPAL, payPalConfig);
        configMap.put(PaymentMethodEnum.VNPAY, vnpayConfig);
        configMap.put(PaymentMethodEnum.ZALOPAY, zaloPayConfig);
        configMap.put(PaymentMethodEnum.MOMO, moMoConfig);
        return configMap;
    }
}
