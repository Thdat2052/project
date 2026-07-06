package com.example.common.service.models.reports;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class MaintenanceStatusReportModel {
    private Long roomId;
    private String roomName;
    private Long maintenanceId;
    private String maintenanceStatus;
    private LocalDateTime requestDate;
    private LocalDateTime requestDoneDate;
    private Long totalFee;
}
