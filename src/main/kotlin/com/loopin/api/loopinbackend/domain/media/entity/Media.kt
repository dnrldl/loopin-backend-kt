package com.loopin.api.loopinbackend.domain.media.entity

import com.loopin.api.loopinbackend.domain.media.type.MediaType
import jakarta.persistence.*

@Entity
@Table(
    name = "medias",
    indexes = [
//        Index(name = "idx_media_target", columnList = "media_type, target_id")
    ]
)
class Media(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val mediaType: MediaType,

    @Column(nullable = false)
    val targetId: Long,

    @Column(nullable = false)
    val src: String,

    @Column
    val description: String? = null,

    @Column(nullable = false)
    val sortOrder: Int
)
