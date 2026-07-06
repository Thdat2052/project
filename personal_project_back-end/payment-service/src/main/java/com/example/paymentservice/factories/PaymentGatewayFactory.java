package com.example.paymentservice.factories;
import com.example.paymentservice.components.*;
import com.example.paymentservice.enums.PaymentMethodEnum;
import org.springframework.stereotype.Component;

@Component
public class PaymentGatewayFactory {

    private final MomoGateway momoGateway;
    private final PaypalGateway paypalGateway;
    private final VnpayGateway vnpayGateway;
    private final ZalopayGateway zalopayGateway;

    public PaymentGatewayFactory(MomoGateway momoGateway, PaypalGateway paypalGateway,
                                 VnpayGateway vnpayGateway, ZalopayGateway zalopayGateway) {
        this.momoGateway = momoGateway;
        this.paypalGateway = paypalGateway;
        this.vnpayGateway = vnpayGateway;
        this.zalopayGateway = zalopayGateway;
    }

    public PaymentGateway getGateway(PaymentMethodEnum method) {
        switch (method) {
            case MOMO:
                return momoGateway;
            case PAYPAL:
                return paypalGateway;
            case VNPAY:
                return vnpayGateway;
            case ZALOPAY:
                return zalopayGateway;
            default:
                throw new IllegalArgumentException("Unsupported payment method: " + method);
        }
    }
}