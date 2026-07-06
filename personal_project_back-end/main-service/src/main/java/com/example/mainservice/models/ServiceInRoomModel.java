package com.example.mainservice.models;

import lombok.Data;

@Data
public class ServiceInRoomModel {
    private Long maintenanceRequestId;
    private Long serviceId;
    private String serviceName;
    private Long price;
    private Integer quantity;
    private Long totalMoney;
}
