package com.example.common.service.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagingQueryConditionRequest {
    private int pageSize = 10;
    private int pageCurrent = 0;
    private String sortBy;
    private String sortType;
    private String keyword;

    public PagingQueryConditionRequest(int pageSize, int pageCurrent) {
        this.pageSize = pageSize;
        this.pageCurrent = pageCurrent;
    }
}
