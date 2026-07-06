package com.example.common.service.models.responses;

import com.example.common.service.enums.EmailProtocolEnum;
import com.example.common.service.enums.EmailSecurityEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailConfigResponse {
    Long id;
    String host;
    Integer port;
    String username;
    String email;
    EmailSecurityEnum security;
    EmailProtocolEnum protocol;
}
