package com.wbsrisktaskerx.wbsrisktaskerx.entity;

import com.wbsrisktaskerx.wbsrisktaskerx.utils.DateTimeUtils;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseTimeEntity {
    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @PreUpdate
    public void updateInit() {
        this.updatedAt = DateTimeUtils.getDateTimeNow();
    }

    @PrePersist
    public void createInit() {
        this.createdAt = DateTimeUtils.getDateTimeNow();
        this.updatedAt = DateTimeUtils.getDateTimeNow();
    }
}

