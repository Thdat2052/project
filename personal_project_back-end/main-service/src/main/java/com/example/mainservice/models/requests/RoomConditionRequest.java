package com.example.mainservice.models.requests;

import com.example.common.service.enums.RoomStatusEnum;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomConditionRequest extends PagingQueryConditionRequest {
    RoomStatusEnum status;
}
