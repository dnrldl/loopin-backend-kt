package com.loopin.api.loopinbackend.domain.user.command.dto

data class UpdateUserInfoCommand(
    val userId: Long,
    val nickname: String?,
    val bio: String?,
) {
    fun hasNickname(): Boolean = !nickname.isNullOrBlank()
    fun hasBio(): Boolean = !bio.isNullOrBlank()
}
