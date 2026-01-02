package com.loopin.api.loopinbackend.common.entity

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy

@MappedSuperclass
abstract class BaseEntity : TimeBaseEntity() {
    @CreatedBy
    @Column(updatable = false)
    var createdBy: Long? = null

    @LastModifiedBy
    var updatedBy: Long? = null
}