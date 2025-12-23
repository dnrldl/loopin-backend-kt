package com.loopin.api.loopinbackend.common.response.code

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val code: String,
    val message: String,
    val status: HttpStatus,
) {
    INTERNAL_SERVER_ERROR("F-E001", "알 수 없는 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // global
    NOT_FOUND("F-G001", "요청하신 URL을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    METHOD_NOT_ALLOWED("F-G002", "허용되지 않은 HTTP 메서드입니다.", HttpStatus.METHOD_NOT_ALLOWED),

    // user
    DUPLICATED_EMAIL("F-U001", "이미 사용 중인 이메일입니다.", HttpStatus.CONFLICT),
    DUPLICATED_NICKNAME("F-U002", "이미 사용 중인 닉네임입니다.", HttpStatus.CONFLICT),
    USER_NOT_FOUND("F-U003", "존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND),
    INVALID_INPUT_VALUE("F-U004", "요청값이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_OLD_PASSWORD("F-U005", "기존 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    SAME_PASSWORD("F-U006", "기존 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    PASSWORD_SAME_AS_OLD("F-U007", "새 비밀번호는 기존 비밀번호와 같을 수 없습니다.", HttpStatus.BAD_REQUEST),
    DUPLICATED_PHONE_NUMBER("F-U008", "이미 사용 중인 전화번호입니다.", HttpStatus.CONFLICT),
    USED_USER_INFORMATION("F-U008", "이미 사용 중인 사용자 정보입니다.", HttpStatus.CONFLICT),

    // post
    POST_NOT_FOUND("F-P001", "게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    POST_NOT_OWNER("F-P002", "게시글의 작성자가 아닙니다.", HttpStatus.FORBIDDEN),

    // post_like
    POST_LIKE_NOT_FOUND("F-PL001", "현재 유저의 해당 게시글 좋아요 기록을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    POST_ALREADY_LIKED("F-PL002", "현재 유저가 해당 게시글을 이미 좋아요 했습니다.", HttpStatus.BAD_REQUEST),
    POST_NOT_LIKED("F-PL003", "현재 유저가 해당 게시글을 좋아요 하지 않았습니다.", HttpStatus.BAD_REQUEST),

    // auth
    INVALID_LOGIN("F-A001", "로그인 실패하였습니다.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("F-A002", "인증 실패하였습니다.", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED("F-A003", "접근 권한이 없습니다.", HttpStatus.FORBIDDEN),

    // jwt (auth)
    INVALID_JWT("F-J001", "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
    EXPIRED_JWT("F-J002", "토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    JWT_VALIDATION_ERROR("F-J003", "JWT 검증 중 예기치 못한 오류가 발생했습니다.", HttpStatus.UNAUTHORIZED),
    EMPTY_JWT("F-J004", "토큰이 비어있습니다.", HttpStatus.UNAUTHORIZED),
    BLACKLISTED_TOKEN("F-J005", "로그아웃된 토큰입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_REFRESH_TOKEN("F-J006", "유효하지 않은 refresh 토큰입니다.", HttpStatus.BAD_REQUEST);
}