package com.example.common.service.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "UTILITY_INDEX")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UtilityIndexEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "ROOM_ID")
    Long roomId;
    @Column(name = "ELECTRIC_OLD_INDEX")
    Long electricityOldIndex;
    @Column(name = "ELECTRIC_NEW_INDEX")
    Long electricityNewIndex;
    @Column(name = "WATER_OLD_INDEX")
    Long waterOldIndex;
    @Column(name = "WATER_NEW_INDEX")
    Long waterNewIndex;
    @Column(name = "WATER_USAGE")
    Long waterUsage;
    @Column(name = "ELECTRIC_USAGE")
    Long electricUsage;
    @Column(name = "MONTH_MEASURE")
    Integer monthMeasure;
    @Column(name = "YEAR_MEASURE")
    Integer yearMeasure;

}
