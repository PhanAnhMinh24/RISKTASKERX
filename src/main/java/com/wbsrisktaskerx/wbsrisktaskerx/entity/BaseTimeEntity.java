package com.wbsrisktaskerx.wbsrisktaskerx.entity;

import com.wbsrisktaskerx.wbsrisktaskerx.utils.DateTimeUtils;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;


@Getter
@Setter
@AllArgsConstructor
@MappedSuperclass
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseTimeEntity {
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updateAt;

    @Column(name = "expires_at", nullable = false)
    private OffsetDateTime expiresAt;

    @PrePersist
    public void onCreate() {
        this.setCreateAt(DateTimeUtils.getDateTimeNow());
        this.setUpdateAt(DateTimeUtils.getDateTimeNow());
        this.setExpiresAt(DateTimeUtils.getDateTimeNow());
    }

    @PreUpdate
    public void onUpdate() {
        this.setUpdateAt(DateTimeUtils.getDateTimeNow());
    }
}

