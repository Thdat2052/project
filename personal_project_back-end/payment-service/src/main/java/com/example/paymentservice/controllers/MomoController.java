package com.example.paymentservice.controllers;

import com.example.paymentservice.configs.MoMoConfig;
import com.example.paymentservice.enums.PaymentMethodEnum;
import com.example.paymentservice.services.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/momo")
@AllArgsConstructor
public class MomoController {

    private final PaymentService paymentService;
    private final Map<PaymentMethodEnum, Object> paymentGatewayConfigs;

    private MoMoConfig getMoMoConfig() {
        return (MoMoConfig) paymentGatewayConfigs.get(PaymentMethodEnum.MOMO);
    }

    @PostMapping
    public ResponseEntity<String> createPayment(@RequestBody Map<String, Object> paymentData) {
        String amount = String.valueOf(paymentData.getOrDefault("amount", "0"));
        String orderInfo = (String) paymentData.getOrDefault("orderInfo", "Thanh toán đơn hàng");

        try {
            MoMoConfig config = getMoMoConfig();
            String response = paymentService.createPayment(
                    PaymentMethodEnum.MOMO,
                    amount,
                    orderInfo,
                    config.getRedirectUrl(),
                    config.getIpnUrl()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating payment: " + e.getMessage());
        }
    }

    @GetMapping("/order-status/{orderId}")
    public ResponseEntity<String> checkPaymentStatus(@PathVariable String orderId) {
        try {
            String response = paymentService.checkPaymentStatus(PaymentMethodEnum.MOMO, orderId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error checking payment status: " + e.getMessage());
        }
    }
}
