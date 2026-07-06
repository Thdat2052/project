package com.example.common.service.models.requests;


import com.example.common.service.enums.EmailProtocolEnum;
import com.example.common.service.enums.EmailSecurityEnum;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)

public class EmailConfigRequest {
    Long id;
    @NonNull
    String host;
    @NonNull
    Integer port;
    @NonNull
    String username;
    @NonNull
    String password;
    @NonNull
    String email;
    @NonNull
    EmailSecurityEnum security;
    @NonNull
    EmailProtocolEnum protocol;
}
