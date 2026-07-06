package com.example.common.service.models.reports;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomRevenueReportModel {
    private Long roomId;
    private String roomName;
    private Integer year;
    private Integer month;
    private Integer quarter;
    private BigDecimal totalRevenue;
}
