package com.example.common.service.entities;

import com.example.common.service.enums.CheckoutRequestStatusEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "CHECKOUT_REQUEST")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckoutRequestEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "ROOM_ID")
    Long roomId;
    @Column(name = "USER_ID")
    Long userId;
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    CheckoutRequestStatusEnum status;
    @Column(name = "REQUEST_DATE")
    LocalDateTime requestDate;
    @Column(name = "REASON")
    String reason;
}
