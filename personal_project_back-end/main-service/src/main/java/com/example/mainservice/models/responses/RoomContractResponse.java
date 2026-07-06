package com.example.mainservice.models.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoomContractResponse {
    private Long roomId;
    private String roomName;
    private String roomNumber;
    private Long userId;
    private String username;
    private LocalDateTime endDate;
}
