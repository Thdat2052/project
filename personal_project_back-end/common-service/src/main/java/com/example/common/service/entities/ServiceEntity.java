package com.example.common.service.entities;

import com.example.common.service.enums.ServiceTypeEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "SERVICE")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "NAME")////electricity, water, internet, cleaning, laundry, parking, security, maintenance
    String name;

    @Column(name = "IS_ACTIVE")
    Boolean isActive;

    @Column(name = "PRICE")
    Long price;

    @Column(name = "SERVICE_TYPE")
    @Enumerated(EnumType.STRING)
    ServiceTypeEnum serviceType;

}
