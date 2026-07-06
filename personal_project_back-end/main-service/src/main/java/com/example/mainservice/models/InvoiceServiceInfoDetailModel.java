package com.example.mainservice.models;

import com.example.common.service.enums.InvoiceStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class InvoiceServiceInfoDetailModel {
    private Long invoiceId;
    private LocalDateTime invoiceCreated;
    private int monthCal;
    private int yearCal;
    private BigDecimal totalInvoice;
    private String fullName;
    private Long roomId;
    private BigDecimal roomPrice;
    private InvoiceStatusEnum status;
    private List<ServiceInfoModel> serviceInfos;
}
