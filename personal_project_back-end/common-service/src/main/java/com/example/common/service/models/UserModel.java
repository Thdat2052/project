package com.example.common.service.models;
import com.example.common.service.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel extends BaseModel implements Serializable {
    @NotBlank(message = "Full name is required")
    private String fullName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "CCCD is required")
    private String cccd;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private String permanentAddress;
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime recordedDate;

    private String licensePlateNumber;
    private String note;

    @NotBlank(message = "Username is required")
    private String username;
    @JsonIgnore
    private String password;
    private String newPassword;
    private String transactionId;
    private Boolean isRoomRepresentative =false;
    private List<RoleEnum> roles;
}