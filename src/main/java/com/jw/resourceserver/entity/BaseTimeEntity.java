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
    @Column(name = DBConstants.Columns.CREATED, nullable = false, updatable = false)
    private LocalDateTime created;

    @CreatedBy
    @Column(name = DBConstants.Columns.CREATED_BY, updatable = false, length = 100)
    private String createdBy;

    @LastModifiedDate
    @Column(name = DBConstants.Columns.UPDATED, nullable = false)
    private LocalDateTime updated;

    @LastModifiedBy
    @Column(name = DBConstants.Columns.UPDATED_BY, length = 100)
    private String updatedBy;

    @Column(name = DBConstants.Columns.IS_DELETED, nullable = false)
    private Boolean isDeleted = false;

    @Column(name = DBConstants.Columns.DELETED)
    private LocalDateTime deleted;

    // Soft Delete 메서드
    public void softDelete() {
        this.isDeleted = true;
        this.deleted = LocalDateTime.now();
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
