package com.example.paymentservice.services.impls;

import com.example.paymentservice.components.PaymentGateway;
import com.example.paymentservice.enums.PaymentMethodEnum;
import com.example.paymentservice.factories.PaymentGatewayFactory;
import com.example.paymentservice.services.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentGatewayFactory factory;

    public PaymentServiceImpl(PaymentGatewayFactory factory) {
        this.factory = factory;
    }

    @Override
    public String createPayment(PaymentMethodEnum method, String amount, String orderInfo,
                                String returnUrl, String notifyUrl) {
        PaymentGateway gateway = factory.getGateway(method);
        return gateway.createPayment(amount, orderInfo, returnUrl, notifyUrl);
    }

    @Override
    public String checkPaymentStatus(PaymentMethodEnum method, String orderId) {
        PaymentGateway gateway = factory.getGateway(method);
        return gateway.checkPaymentStatus(orderId);
    }
}
