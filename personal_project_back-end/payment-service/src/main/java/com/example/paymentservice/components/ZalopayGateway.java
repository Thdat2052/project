package com.example.paymentservice.components;


import com.example.paymentservice.configs.ZaloPayConfig;
import com.example.paymentservice.enums.PaymentMethodEnum;
import com.example.paymentservice.utils.cryptos.HMACUtil;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ZalopayGateway implements PaymentGateway {

    private final Map<PaymentMethodEnum, Object> configMap;

    public ZalopayGateway(Map<PaymentMethodEnum, Object> configMap) {
        this.configMap = configMap;
    }

    private ZaloPayConfig getConfig() {
        return (ZaloPayConfig) configMap.get(PaymentMethodEnum.ZALOPAY);
    }

    private static String getCurrentTimeString(String format) {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setCalendar(cal);
        return fmt.format(cal.getTimeInMillis());
    }

    @Override
    public String createPayment(String amount, String orderInfo, String returnUrl, String notifyUrl) {
        ZaloPayConfig config = getConfig();
        Random rand = new Random();
        int randomId = rand.nextInt(1000000);

        Map<String, Object> order = new HashMap<>();
        order.put("app_id", config.getAppId());
        order.put("app_trans_id", getCurrentTimeString("yyMMdd") + "_" + randomId);
        order.put("app_time", System.currentTimeMillis());
        order.put("app_user", "user123");
        order.put("amount", amount);
        order.put("description", orderInfo + " #" + randomId);
        order.put("bank_code", "");
        order.put("item", "[{}]");
        order.put("embed_data", "{}");
        order.put("callback_url", notifyUrl);

        String data = order.get("app_id") + "|" + order.get("app_trans_id") + "|" + order.get("app_user") + "|"
                + order.get("amount") + "|" + order.get("app_time") + "|" + order.get("embed_data") + "|"
                + order.get("item");

        String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, config.getKey1(), data);
        order.put("mac", mac);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(config.getEndpoint());
            List<NameValuePair> params = new ArrayList<>();
            for (Map.Entry<String, Object> entry : order.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }

            post.setEntity(new UrlEncodedFormEntity(params));

            try (CloseableHttpResponse response = client.execute(post)) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder resultJsonStr = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    resultJsonStr.append(line);
                }
                return resultJsonStr.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"Failed to create order: " + e.getMessage() + "\"}";
        }
    }

    @Override
    public String checkPaymentStatus(String appTransId) {
        ZaloPayConfig config = getConfig();
        String data = config.getAppId() + "|" + appTransId + "|" + config.getKey1();
        String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, config.getKey1(), data);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(config.getOrderStatusUrl());
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("app_id", config.getAppId()));
            params.add(new BasicNameValuePair("app_trans_id", appTransId));
            params.add(new BasicNameValuePair("mac", mac));

            post.setEntity(new UrlEncodedFormEntity(params));

            try (CloseableHttpResponse response = client.execute(post)) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder resultJsonStr = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    resultJsonStr.append(line);
                }
                return resultJsonStr.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"Failed to get order status: " + e.getMessage() + "\"}";
        }
    }
}