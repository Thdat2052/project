package com.example.paymentservice.controllers;

import com.example.paymentservice.configs.ZaloPayConfig;
import com.example.paymentservice.enums.PaymentMethodEnum;
import com.example.paymentservice.services.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/zalopay")
@AllArgsConstructor
public class ZalopayController {

    private final PaymentService paymentService;
    private final Map<PaymentMethodEnum, Object> paymentGatewayConfigs;

    private ZaloPayConfig getZaloPayConfig() {
        return (ZaloPayConfig) paymentGatewayConfigs.get(PaymentMethodEnum.ZALOPAY);
    }

    @PostMapping
    public ResponseEntity<String> createPayment(@RequestBody Map<String, Object> orderRequest) {
        try {
            ZaloPayConfig config = getZaloPayConfig();
            String amount = String.valueOf(orderRequest.getOrDefault("amount", "0"));
            String orderInfo = (String) orderRequest.getOrDefault("description", "SN Mobile - Payment");

            String response = paymentService.createPayment(
                    PaymentMethodEnum.ZALOPAY,
                    amount,
                    orderInfo,
                    config.getReturnUrl(),  // Lấy từ ZaloPayConfig
                    config.getNotifyUrl()   // Lấy từ ZaloPayConfig
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating payment: " + e.getMessage());
        }
    }

    @GetMapping("/order-status/{appTransId}")
    public ResponseEntity<String> getOrderStatus(@PathVariable String appTransId) {
        try {
            String response = paymentService.checkPaymentStatus(PaymentMethodEnum.ZALOPAY, appTransId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error checking order status: " + e.getMessage());
        }
    }
}
