package com.example.common.service.entities;

import com.example.common.service.enums.EmailProtocolEnum;
import com.example.common.service.enums.EmailSecurityEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Table(name = "EMAIL_CONFIG")
@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailConfigEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "HOST")
    String host;

    @Column(name = "PORT")
    Integer port;

    @Column(name = "USERNAME")
    String username;

    @Column(name = "PASSWORD")
    String password;

    @Column(name = "EMAIL")
    String email;

    @Column(name = "SECURITY")
    @Enumerated(EnumType.STRING)
    EmailSecurityEnum security;

    @Column(name = "PROTOCOL")
    @Enumerated(EnumType.STRING)
    EmailProtocolEnum protocol;

}
