package com.example.common.service.entities;

import com.example.common.service.enums.RoomStatusEnum;
import com.example.common.service.enums.RoomTypeEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "ROOM")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "NAME")
    String name;
    @Column(name = "NUMBER")
    String number;
    @Column(name = "PRICE")
    Long price;
    @Column(name = "LENGTH")
    Long length;
    @Column(name = "WIDTH")
    Long width;
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    RoomStatusEnum status;
    @Column(name = "NOTE")
    String note;
}