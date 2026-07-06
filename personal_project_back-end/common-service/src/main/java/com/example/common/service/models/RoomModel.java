package com.example.common.service.models;
import com.example.common.service.enums.RoomStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomModel extends BaseModel {
    @NotBlank(message = "Room number is required")
    private String number;

    private Double price = 0.0;

    private Double length;
    private Double width;

    @NotNull(message = "Status is required")
    private RoomStatusEnum status;

    private String note;
}