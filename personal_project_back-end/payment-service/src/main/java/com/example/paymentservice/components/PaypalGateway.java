package com.example.paymentservice.components;

import com.example.paymentservice.configs.PayPalConfig;
import com.example.paymentservice.enums.PaymentMethodEnum;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class PaypalGateway implements PaymentGateway {

    private final Map<PaymentMethodEnum, Object> configMap;

    public PaypalGateway(Map<PaymentMethodEnum, Object> configMap) {
        this.configMap = configMap;
    }

    private APIContext getApiContext() {
        PayPalConfig config = (PayPalConfig) configMap.get(PaymentMethodEnum.PAYPAL);
        return new APIContext(config.getClientId(), config.getClientSecret(), config.getMode());
    }

    @Override
    public String createPayment(String amount, String orderInfo, String returnUrl, String notifyUrl) {
        APIContext apiContext = getApiContext();
        try {
            Amount paypalAmount = new Amount();
            paypalAmount.setTotal(amount);
            paypalAmount.setCurrency("USD"); // Có thể thay đổi tùy cấu hình

            Transaction transaction = new Transaction();
            transaction.setAmount(paypalAmount);
            transaction.setDescription(orderInfo);

            Payer payer = new Payer();
            payer.setPaymentMethod("paypal");

            Payment payment = new Payment();
            payment.setIntent("sale");
            payment.setPayer(payer);
            payment.setTransactions(Arrays.asList(transaction));

            RedirectUrls redirectUrls = new RedirectUrls();
            redirectUrls.setCancelUrl(notifyUrl); // Dùng notifyUrl làm cancelUrl
            redirectUrls.setReturnUrl(returnUrl);
            payment.setRedirectUrls(redirectUrls);

            Payment createdPayment = payment.create(apiContext);

            List<Links> links = createdPayment.getLinks();
            for (Links link : links) {
                if ("approval_url".equalsIgnoreCase(link.getRel())) {
                    return link.getHref();
                }
            }
            throw new PayPalRESTException("Approval URL not found");
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return "{\"error\": \"Failed to create payment: " + e.getMessage() + "\"}";
        }
    }

    @Override
    public String checkPaymentStatus(String paymentId) {
        APIContext apiContext = getApiContext();
        try {
            Payment payment = Payment.get(apiContext, paymentId);
            return payment.toJSON(); // Trả về JSON trạng thái thanh toán
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return "{\"error\": \"Failed to check payment status: " + e.getMessage() + "\"}";
        }
    }
}
