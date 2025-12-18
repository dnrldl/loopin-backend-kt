package com.loopin.api.loopinbackend.common.response.code

enum class SuccessCode(
    val code: String,
    val message: String,
) {
    REQUEST_SUCCESS("S-001", "요청 성공했습니다."),
    RETRIEVE_SUCCESS("S-002", "조회되었습니다."),
    SAVE_SUCCESS("S-003", "저장되었습니다."),
    UPDATE_SUCCESS("S-004", "수정되었습니다"),
    DELETE_SUCCESS("S-004", "삭제되었습니다"),
}