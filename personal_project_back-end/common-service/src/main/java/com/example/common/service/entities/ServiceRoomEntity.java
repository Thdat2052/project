package com.example.common.service.entities;

import com.example.common.service.enums.ServiceRoomStatusEnum;
import com.example.common.service.enums.ServiceTypeEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "SERVICE_ROOM")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceRoomEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "CONTRACT_ID")
    Long contractId;
    @Column(name = "SERVICE_ID")
    Long serviceId;
    @Column(name = "QUANTITY")
    Long quantity;
    @Column(name = "PRICE")
    Long price;

//    @Column(name = "SERVICE_TYPE")
//    @Enumerated(EnumType.STRING)
//    ServiceTypeEnum serviceType;
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    ServiceRoomStatusEnum status;
}
