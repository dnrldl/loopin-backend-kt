package com.loopin.api.loopinbackend.domain.user.repository

import com.loopin.api.loopinbackend.domain.user.entity.QUser
import org.springframework.stereotype.Repository

@Repository
class UserQueryRepository {

    private val user = QUser.user
}