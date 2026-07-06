package com.example.common.service.models;
import com.example.common.service.enums.ContractStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractModel extends BaseModel {
    @NotNull(message = "Room ID is required")
    private Long roomId;

    // @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @NotNull(message = "Deposit amount is required")
    @Positive(message = "Deposit must be greater than zero")
    private Double deposit;

    @NotNull(message = "Monthly rent is required")
    @Positive(message = "Monthly rent must be greater than zero")
    private Double monthlyRent;
    private String transactionId;
    private ContractStatusEnum status = ContractStatusEnum.ACTIVE;
}