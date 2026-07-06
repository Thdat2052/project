package com.example.paymentservice.components;

import com.example.paymentservice.configs.MoMoConfig;
import com.example.paymentservice.enums.PaymentMethodEnum;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
public class MomoGateway implements PaymentGateway {

    private final Map<PaymentMethodEnum, Object> configMap;

    public MomoGateway(Map<PaymentMethodEnum, Object> configMap) {
        this.configMap = configMap;
    }

    private MoMoConfig getConfig() {
        return (MoMoConfig) configMap.get(PaymentMethodEnum.MOMO);
    }

    @Override
    public String createPayment(String amount, String orderInfo, String returnUrl, String notifyUrl) {
        MoMoConfig config = getConfig();
        try {
            String requestId = config.getPartnerCode() + new Date().getTime();
            String orderId = requestId;
            String extraData = "";

            String rawSignature = String.format(
                    "accessKey=%s&amount=%s&extraData=%s&ipnUrl=%s&orderId=%s&orderInfo=%s&partnerCode=%s&redirectUrl=%s&requestId=%s&requestType=%s",
                    config.getAccessKey(), amount, extraData, notifyUrl, orderId, orderInfo,
                    config.getPartnerCode(), returnUrl, requestId, config.getRequestType());

            String signature = signHmacSHA256(rawSignature, config.getSecretKey());

            JSONObject requestBody = new JSONObject();
            requestBody.put("partnerCode", config.getPartnerCode());
            requestBody.put("accessKey", config.getAccessKey());
            requestBody.put("requestId", requestId);
            requestBody.put("amount", amount);
            requestBody.put("orderId", orderId);
            requestBody.put("orderInfo", orderInfo);
            requestBody.put("redirectUrl", returnUrl);
            requestBody.put("ipnUrl", notifyUrl);
            requestBody.put("extraData", extraData);
            requestBody.put("requestType", config.getRequestType());
            requestBody.put("signature", signature);
            requestBody.put("lang", "en");

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("https://test-payment.momo.vn/v2/gateway/api/create");
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(requestBody.toString(), StandardCharsets.UTF_8));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"Failed to create payment request: " + e.getMessage() + "\"}";
        }
    }

    @Override
    public String checkPaymentStatus(String orderId) {
        MoMoConfig config = getConfig();
        try {
            String requestId = config.getPartnerCode() + new Date().getTime();
            String rawSignature = String.format(
                    "accessKey=%s&orderId=%s&partnerCode=%s&requestId=%s",
                    config.getAccessKey(), orderId, config.getPartnerCode(), requestId);

            String signature = signHmacSHA256(rawSignature, config.getSecretKey());

            JSONObject requestBody = new JSONObject();
            requestBody.put("partnerCode", config.getPartnerCode());
            requestBody.put("accessKey", config.getAccessKey());
            requestBody.put("requestId", requestId);
            requestBody.put("orderId", orderId);
            requestBody.put("signature", signature);
            requestBody.put("lang", "en");

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("https://test-payment.momo.vn/v2/gateway/api/query");
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(requestBody.toString(), StandardCharsets.UTF_8));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"Failed to check payment status: " + e.getMessage() + "\"}";
        }
    }

    private static String signHmacSHA256(String data, String key) throws Exception {
        Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        hmacSHA256.init(secretKey);
        byte[] hash = hmacSHA256.doFinal(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}