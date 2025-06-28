package com.jw.resourceserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(name = DatabaseConstants.Columns.CREATED, nullable = false, updatable = false)
    private LocalDateTime created;

    @CreatedBy
    @Column(name = DatabaseConstants.Columns.CREATED_BY, updatable = false, length = 100)
    private String createdBy;

    @LastModifiedDate
    @Column(name = DatabaseConstants.Columns.UPDATED, nullable = false)
    private LocalDateTime updated;

    @LastModifiedBy
    @Column(name = DatabaseConstants.Columns.UPDATED_BY, length = 100)
    private String updatedBy;

    @Column(name = DatabaseConstants.Columns.IS_DELETED, nullable = false)
    private Boolean isDeleted = false;

    // Soft Delete 메서드
    public void softDelete() {
        this.isDeleted = true;
    }

    public void restore() {
        this.isDeleted = false;
    }

    @PrePersist
    protected void onCreate() {
        if (created == null) {
            created = LocalDateTime.now();
        }
        if (updated == null) {
            updated = LocalDateTime.now();
        }
        if (isDeleted == null) {
            isDeleted = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updated = LocalDateTime.now();
    }
}
