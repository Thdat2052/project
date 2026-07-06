package com.example.paymentservice.components;

public interface PaymentGateway {
    String createPayment(String amount, String orderInfo, String returnUrl, String notifyUrl); // Tạo thanh toán
    String checkPaymentStatus(String orderId); // Kiểm tra trạng thái
}