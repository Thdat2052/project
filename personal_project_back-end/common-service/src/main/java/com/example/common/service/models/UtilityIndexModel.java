package com.example.common.service.models;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UtilityIndexModel extends BaseModel {

    private Long id;

    @NotNull(message = "Room ID must not be null")
    private Long roomId;
    private String roomNumber;

    @NotNull(message = "Electricity old index must not be null")
    private Long electricityOldIndex;

    @NotNull(message = "Electricity new index must not be null")
    private Long electricityNewIndex;

    @NotNull(message = "Water old index must not be null")
    private Long waterOldIndex;

    @NotNull(message = "Water new index must not be null")
    private Long waterNewIndex;

    private Long waterUsage;
    private Long electricUsage;
    Integer monthMeasure;
    Integer yearMeasure;
}
