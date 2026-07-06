package com.example.mainservice.models.requests;

import com.example.common.service.enums.MaintenanceRequestStatusEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaintenanceRequestRequest {
    MaintenanceRequestStatusEnum status;
    Double price;
    Long id;
}
