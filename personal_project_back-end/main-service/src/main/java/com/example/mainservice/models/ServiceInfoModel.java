package com.example.mainservice.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceInfoModel {
    private Long serviceId;
    private String nameService;
    private String serviceType;
    private BigDecimal priceService;
    private Integer quantityService;
}
