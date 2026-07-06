package com.example.paymentservice.components;

import com.example.paymentservice.configs.VNPayConfig;
import com.example.paymentservice.enums.PaymentMethodEnum;
import com.example.paymentservice.utils.CommonUtils;
import com.example.paymentservice.utils.cryptos.CryptoUtils;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class VnpayGateway implements PaymentGateway {

    private final Map<PaymentMethodEnum, Object> configMap;

    public VnpayGateway(Map<PaymentMethodEnum, Object> configMap) {
        this.configMap = configMap;
    }

    private VNPayConfig getConfig() {
        return (VNPayConfig) configMap.get(PaymentMethodEnum.VNPAY);
    }

    @Override
    public String createPayment(String amount, String orderInfo, String returnUrl, String notifyUrl) {
        VNPayConfig config = getConfig();
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";

        long vnpAmount;
        try {
            vnpAmount = Long.parseLong(amount) * 100;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Số tiền không hợp lệ");
        }

        String bankCode = "NCB";
        String vnp_TxnRef = CommonUtils.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", config.getTmnCode());
        vnp_Params.put("vnp_Amount", String.valueOf(vnpAmount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orderInfo + ": " + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", returnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        try {
            for (String fieldName : fieldNames) {
                String fieldValue = vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    hashData.append(fieldName).append('=')
                            .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()))
                            .append('=')
                            .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    query.append('&');
                    hashData.append('&');
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "{\"error\": \"Failed to encode parameters: " + e.getMessage() + "\"}";
        }

        if (query.length() > 0) query.setLength(query.length() - 1);
        if (hashData.length() > 0) hashData.setLength(hashData.length() - 1);

        String vnp_SecureHash = CryptoUtils.hmacSHA512(config.getSecretKey(), hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);
        return config.getPayUrl() + "?" + query.toString();
    }

    @Override
    public String checkPaymentStatus(String orderId) {
        // VNPay không có API kiểm tra trạng thái trực tiếp trong code gốc,
        // nên tạm thời trả về thông báo chưa hỗ trợ
        return "{\"error\": \"VNPay status check not implemented yet\"}";
    }
}