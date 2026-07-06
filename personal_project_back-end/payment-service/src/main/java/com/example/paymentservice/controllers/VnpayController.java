package com.example.paymentservice.controllers;

import com.example.paymentservice.configs.VNPayConfig;
import com.example.paymentservice.enums.PaymentMethodEnum;
import com.example.paymentservice.services.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/vnpay")
@AllArgsConstructor
public class VnpayController {

    private final PaymentService paymentService;
    private final Map<PaymentMethodEnum, Object> paymentGatewayConfigs;

    private VNPayConfig getVNPayConfig() {
        return (VNPayConfig) paymentGatewayConfigs.get(PaymentMethodEnum.VNPAY);
    }

    @PostMapping
    public ResponseEntity<String> createPayment(@RequestBody Map<String, Object> paymentData) {
        try {
            String amount = String.valueOf(paymentData.getOrDefault("amount", "0"));
            String orderInfo = (String) paymentData.getOrDefault("orderInfo", "Thanh toán đơn hàng");
            String returnUrl = getVNPayConfig().getReturnUrl();
            String notifyUrl = (String) paymentData.getOrDefault("notifyUrl", "http://localhost:8080/api/vnpay/notify");

            String paymentUrl = paymentService.createPayment(
                    PaymentMethodEnum.VNPAY,
                    amount,
                    orderInfo,
                    returnUrl,
                    notifyUrl
            );
            return ResponseEntity.ok(paymentUrl);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đã xảy ra lỗi khi tạo thanh toán!");
        }
    }

    @GetMapping("/return")
    public ResponseEntity<String> returnPayment(@RequestParam("vnp_ResponseCode") String responseCode) {
        // Logic xử lý return đặc thù của VNPay
        if ("00".equals(responseCode)) {
            return ResponseEntity.ok("Thanh toán thành công!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Thanh toán thất bại! Mã lỗi: " + responseCode);
        }
    }
}