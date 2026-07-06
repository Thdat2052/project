package com.example.mainservice.models;

import com.example.common.service.enums.InvoiceStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InvoiceServiceInfoModel {
    private Long invoiceId;
    private LocalDateTime invoiceCreated;
    private int monthCal;
    private int yearCal;
    private BigDecimal totalInvoice;
    private String fullName;
    private Long roomId;
    private Long serviceId;
    private String nameService;
    private String serviceType;
    private BigDecimal priceService;
    private Integer quantityService;
    private InvoiceStatusEnum status;
    private BigDecimal roomPrice;
}
