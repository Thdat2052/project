package com.example.common.service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class BaseEntity {
    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @LastModifiedDate
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt = LocalDateTime.now();

}