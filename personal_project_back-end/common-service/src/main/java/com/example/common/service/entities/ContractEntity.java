package com.example.common.service.entities;

import com.example.common.service.enums.ContractStatusEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "CONTRACT")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "DEPOSIT")
    Long deposit;
    @Column(name = "MONTHLY_RENT")
    Long monthlyRent;
    @Column(name = "USER_ID")
    Long userId;
    @Column(name = "ROOM_ID")
    Long roomId;
    @Column(name = "START_DATE")
    LocalDateTime startDate;
    @Column(name = "END_DATE")
    LocalDateTime endDate;
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    ContractStatusEnum status;
    @Column(name = "TRANSACTION_ID")
    String transactionId;
}
