package com.example.mainservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MonthYearModel {
    private Integer month;
    private Integer year;
}