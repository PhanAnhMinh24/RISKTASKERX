package com.wbsrisktaskerx.wbsrisktaskerx.entity;

import com.wbsrisktaskerx.wbsrisktaskerx.utils.DateTimeUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updateAt;

    @PrePersist
    public void onCreate() {
        this.setCreateAt(DateTimeUtil.getDateTimeNow());
        this.setUpdateAt(DateTimeUtil.getDateTimeNow());
    }

    @PreUpdate
    public void onUpdate() {
        this.setUpdateAt(DateTimeUtil.getDateTimeNow());
    }
}

