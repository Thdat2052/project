package com.example.common.service.models;
import com.example.common.service.enums.ServiceRoomStatusEnum;
import com.example.common.service.enums.ServiceTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceModel extends BaseModel {
    @NotBlank(message = "Service name is required")
    private String name;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Long price;
    private Long quantity;
    private Boolean isActive;
    private ServiceRoomStatusEnum status;

    ServiceTypeEnum serviceType;
}
