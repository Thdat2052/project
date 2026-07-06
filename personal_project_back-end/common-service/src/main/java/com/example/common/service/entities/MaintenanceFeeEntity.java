package com.example.common.service.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "MAINTENANCE_FEE")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaintenanceFeeEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "MAINTENANCE_REQUEST_ID")
    Long maintenanceRequestId;
    @Column(name = "SERVICE_ROOM_ID")
    Long serviceRoomId;
    @Column(name = "PRICE")
    Double price;
//    @Column(name = "TOTAL_FEE")
//    Double totalFee;
}
