package com.example.mainservice.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnpaidCustomerResponse {
    private Long invoiceId;
    private Long roomId;
    private String roomName;
    private String roomNumber;
    private Long userId;
    private String username;
    private String status;
    private BigDecimal totalPrice;
    private Date paymentDate;
}
