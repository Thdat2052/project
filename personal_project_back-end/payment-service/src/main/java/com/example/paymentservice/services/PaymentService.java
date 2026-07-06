package com.example.paymentservice.services;

import com.example.paymentservice.enums.PaymentMethodEnum;

public interface PaymentService {
    String createPayment(PaymentMethodEnum method, String amount, String orderInfo, String returnUrl, String notifyUrl);
    String checkPaymentStatus(PaymentMethodEnum method, String orderId);
}