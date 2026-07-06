package com.example.mainservice.models.responses;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class RevenueStatResponse {
    private Integer year;
    private Integer month;
    private Integer quarter;
    private BigDecimal totalRevenue;
}
