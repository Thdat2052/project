package com.example.common.service.entities;

import com.example.common.service.enums.UserStatusEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "USER")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "FULLNAME")
    String fullName;
    @Column(name = "EMAIL")
    String email;
    @Column(name = "PHONE_NUMBER")
    String phoneNumber;
    @Column(name = "CCCD")
    String cccd;
    @Column(name = "PERMANENT_ADDRESS")
    String permanentAddress;
    @Column(name = "DATE_OF_BIRTH")
    LocalDateTime dateOfBirth;
    @Column(name = "RECORDED_AT")
    LocalDateTime recordedDate;
    @Column(name = "LICENSE_PLATE_NUMBER")
    String licensePlateNumber;
    @Column(name = "NOTE")
    String note;
    @Column(name = "USERNAME")
    String username;
    @Column(name = "PASSWORD")
    String password;
    @Column(name = "TRANSACTION_ID")
    String transactionId;
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    UserStatusEnum STATUS;

}
