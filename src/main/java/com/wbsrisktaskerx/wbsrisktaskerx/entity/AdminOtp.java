package com.wbsrisktaskerx.wbsrisktaskerx.entity;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EntityConstant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = EntityConstant.ADMIN_OTP_TABLE)
public class AdminOtp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "verification_code", length = 6)
    String verificationCode;

    @Column(name = "expires_at", nullable = false)
    OffsetDateTime expiresAt;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    Admin admin;
}
