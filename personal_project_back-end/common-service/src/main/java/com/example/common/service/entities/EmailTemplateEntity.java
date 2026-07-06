package com.example.common.service.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "EMAIL_TEMPLATE")
@Data
public class EmailTemplateEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "NAME")
    String name;
    @Column(name = "SUBJECT")
    String subject;
    @Column(name = "CONTENT")
    String content;
}
