package com.example.common.service.entities;

import com.example.common.service.enums.InvoiceStatusEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "INVOICE")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "CONTRACT_ID")
    Long contractId;
    @Column(name = "NOTE")
    String note;
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    InvoiceStatusEnum status;
    @Column(name = "TOTAL_PRICE")
    Long totalPrice;
    @Column(name = "DUE_DATE")
    LocalDateTime dueDate;
    @Column(name = "PAYMENT_DATE")
    LocalDateTime paymentDate;
    @Column(name = "PAYMENT_ID")
    Long paymentId;

    @Column(name = "YEAR_CALCULATE")
    Integer yearCalculate;

    @Column(name = "MONTH_CALCULATE")
    Integer monthCalculate;
}
