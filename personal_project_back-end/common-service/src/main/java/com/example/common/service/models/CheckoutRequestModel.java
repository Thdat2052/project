package com.example.common.service.models;

import com.example.common.service.enums.CheckoutRequestStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutRequestModel extends BaseModel {
    private Long roomId;
    private String roomName;
    private Long userId;
    private String fullName;
    private LocalDateTime requestDate;
    private String reason;
    private CheckoutRequestStatusEnum status = CheckoutRequestStatusEnum.PENDING;
}
