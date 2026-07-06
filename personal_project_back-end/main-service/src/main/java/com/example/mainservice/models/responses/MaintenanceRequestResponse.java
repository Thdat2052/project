package com.example.mainservice.models.responses;

import com.example.common.service.enums.MaintenanceRequestStatusEnum;
import com.example.common.service.models.MaintenanceRequestModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaintenanceRequestResponse extends MaintenanceRequestModel {
    String roomNumber;
    MaintenanceRequestStatusEnum status;
    String serviceName;
    Date requestDate;
    Date requestDoneDate;
    Double totalFee;
}
