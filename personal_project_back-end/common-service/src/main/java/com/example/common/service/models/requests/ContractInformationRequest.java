package com.example.common.service.models.requests;

import com.example.common.service.models.ContractModel;
import com.example.common.service.models.ServiceModel;
import com.example.common.service.models.UserModel;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractInformationRequest {

    @NotNull(message = "Member of rooms is required")
    List<UserModel> memberOfRooms;

    @NotNull(message = "Room id is required")
    Long roomId;

    @NotNull(message = "Service of rooms is required")
    List<ServiceModel> serviceOfRooms;

    @NotNull(message = "Contract is required")
    ContractModel contract;
}