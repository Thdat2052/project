package com.example.common.service.entities;

import com.example.common.service.enums.MaintenanceRequestStatusEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "MAINTENANCE_REQUEST")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaintenanceRequestEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    MaintenanceRequestStatusEnum status;
    @Column(name = "SERVICE_ROOM_ID")
    Long serviceRoomId;
    @Column(name = "CONTRACT_ID")
    Long contractId;
    @Column(name = "REQUEST_DATE")
    LocalDateTime requestDate;
    @Column(name = "REQUEST_DONE_DATE")
    LocalDateTime requestDoneDate;
    @Column(name = "DECISION")
    String decision;
    @Column(name = "TOTAL_FEE")
    Double totalFee;
}
