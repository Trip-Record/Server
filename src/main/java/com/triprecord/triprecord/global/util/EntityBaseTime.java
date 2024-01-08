package com.triprecord.triprecord.global.util;


import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class EntityBaseTime {
    @CreatedDate
    private LocalDateTime createdTime;

    @PrePersist
    public void setCreatedTime() {
        this.createdTime = LocalDateTime.now();
    }
}