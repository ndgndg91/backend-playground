package com.ndgndg91.distributedlock.domain

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {
    @LastModifiedDate
    @Column(name = "last_modified_at", nullable = true)
    private var lastModifiedAt: LocalDateTime? = null

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private var createdAt: LocalDateTime? = null
}