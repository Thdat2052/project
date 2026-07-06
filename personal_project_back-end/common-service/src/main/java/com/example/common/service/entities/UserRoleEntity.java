package com.example.common.service.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "USER_ROLE")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRoleEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "USER_ID")
    Long userId;
    @Column(name = "ROLE_ID")
    Long roleId;
}
