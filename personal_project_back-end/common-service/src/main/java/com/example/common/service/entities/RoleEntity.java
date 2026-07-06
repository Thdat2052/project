package com.example.common.service.entities;

import com.example.common.service.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "ROLE")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "NAME")
    @Enumerated(EnumType.STRING)
    RoleEnum name;
}
