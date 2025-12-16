package com.loopin.api.loopinbackend.global.entity

import jakarta.persistence.Access
import jakarta.persistence.AccessType
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
@Access(AccessType.FIELD)
abstract class BaseEntity {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    protected lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    @Column(nullable = false)
    protected lateinit var updatedAt: LocalDateTime
}
