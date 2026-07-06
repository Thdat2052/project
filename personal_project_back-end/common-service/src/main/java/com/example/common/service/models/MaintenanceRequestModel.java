package com.example.common.service.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class MaintenanceRequestModel  extends BaseModel {
    @NotNull
    Long contractId;
    @NotNull
    Long serviceRoomId;
    String decision;
}
