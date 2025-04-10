package com.wbsrisktaskerx.wbsrisktaskerx.entity;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EntityConstant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = EntityConstant.ADMIN_TOKENS_TABLE)
public class AdminToken extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    Admin admin;

    @Column(name = "access_token", length = 500, nullable = false)
    String accessToken;

    @Column(name = "refresh_token", length = 500)
    String refreshToken;

    @Column(name = "expires_at" ,nullable = false)
    OffsetDateTime expiresAt;
}
