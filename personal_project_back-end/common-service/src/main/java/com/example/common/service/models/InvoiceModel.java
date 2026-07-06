package com.example.common.service.models;

import com.example.common.service.enums.InvoiceStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceModel {
    private Long roomId;
    private Long contractId;
    private Long invoiceId;
    private Long userId;
    private LocalDateTime invoiceCreated;
    private InvoiceStatusEnum invoiceStatus;
    private Integer monthCalculate;
    private Integer yearCalculate;

    public InvoiceModel(Long contractId, Long invoiceId, LocalDateTime invoiceCreated, InvoiceStatusEnum invoiceStatus) {
        this.contractId = contractId;
        this.invoiceId = invoiceId;
        this.invoiceCreated = invoiceCreated;
        this.invoiceStatus = invoiceStatus;
    }
    public InvoiceModel(Long contractId, Long invoiceId, LocalDateTime invoiceCreated) {
        this.contractId = contractId;
        this.invoiceId = invoiceId;
        this.invoiceCreated = invoiceCreated;
    }
}
