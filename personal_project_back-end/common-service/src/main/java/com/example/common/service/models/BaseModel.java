package com.example.common.service.models;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseModel implements Serializable {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}