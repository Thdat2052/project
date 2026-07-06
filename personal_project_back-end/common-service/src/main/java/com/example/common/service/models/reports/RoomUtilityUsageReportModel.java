package com.example.common.service.models.reports;

import lombok.Data;

@Data
public class RoomUtilityUsageReportModel {
    private Long roomId;
    private String roomName;
    private Integer totalWaterUsage;
    private Integer totalElectricUsage;
    private Integer monthInventory;
    private Integer yearInventory;
}
