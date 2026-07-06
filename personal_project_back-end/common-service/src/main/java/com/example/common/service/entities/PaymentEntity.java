package com.example.common.service.entities;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "ROOM")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "METHOD")
    String method;
    @Column(name = "STATUS")
    String status;
}
