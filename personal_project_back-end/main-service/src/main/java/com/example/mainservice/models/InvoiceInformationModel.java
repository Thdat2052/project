package com.example.mainservice.models;

import com.example.common.service.enums.ServiceTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class InvoiceInformationModel extends ServiceInRoomModel{

    private Long roomId;
    private String roomName;

    private Long contractId;
    private Long monthlyRent;

    private Long userId;
    private String username;
    @JsonIgnore
    private Long serviceRoomId;
    @JsonIgnore
    private ServiceTypeEnum serviceType;
}
