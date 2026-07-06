package com.example.common.service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@AllArgsConstructor
@Data
@Builder
public class QueryInfoModel {
    private String sql;
    private String countSql;
    private Map<String, Object> params;
}
