package com.example.paymentservice.controllers;

import com.example.paymentservice.configs.PayPalConfig;
import com.example.paymentservice.enums.PaymentMethodEnum;
import com.example.paymentservice.services.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/paypal")
@AllArgsConstructor
public class PaypalController {

    private final PaymentService paymentService;
    private final Map<PaymentMethodEnum, Object> paymentGatewayConfigs;

    private PayPalConfig getPayPalConfig() {
        return (PayPalConfig) paymentGatewayConfigs.get(PaymentMethodEnum.PAYPAL);
    }

    @PostMapping
    public ResponseEntity<String> createPayment(@RequestBody Map<String, Object> paymentData) {
        String amount = String.valueOf(paymentData.getOrDefault("amount", "0"));
        String orderInfo = (String) paymentData.getOrDefault("orderInfo", "Thanh toán đơn hàng");

        try {
            PayPalConfig config = getPayPalConfig();
            String approvalUrl = paymentService.createPayment(
                    PaymentMethodEnum.PAYPAL,
                    amount,
                    orderInfo,
                    config.getSuccessUrl(),
                    config.getCancelUrl()
            );
            return ResponseEntity.ok(approvalUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating payment: " + e.getMessage());
        }
    }

    @GetMapping("/success")
    public ResponseEntity<String> success(@RequestParam("paymentId") String paymentId,
                                          @RequestParam("PayerID") String payerId) {
        return ResponseEntity.ok("Payment Success! Payment ID: " + paymentId);
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> cancel() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment was canceled");
    }
}
