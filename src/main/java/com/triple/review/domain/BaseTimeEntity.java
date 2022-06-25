package com.triple.review.domain;

import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    // @CreatedDate
    private LocalDateTime createdTime;

    // @LastModifiedDate
    private LocalDateTime updatedTime;

    @PrePersist
    public void onPrePersist() {
        this.createdTime = LocalDateTime.now(ZoneOffset.UTC);
        this.updatedTime = this.createdTime;
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedTime = LocalDateTime.now(ZoneOffset.UTC);
    }
}
