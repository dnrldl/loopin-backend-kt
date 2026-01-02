package com.loopin.api.loopinbackend.common.response.code

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val code: String,
    val message: String,
    val status: HttpStatus,
) {
    INTERNAL_SERVER_ERROR("F-ER001", "알 수 없는 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // common
    NOT_FOUND("F-CO001", "요청하신 URL을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    METHOD_NOT_ALLOWED("F-CO002", "허용되지 않은 HTTP 메서드입니다.", HttpStatus.METHOD_NOT_ALLOWED),

    // user
    DUPLICATED_EMAIL("F-USS001", "이미 사용 중인 이메일입니다.", HttpStatus.CONFLICT),
    DUPLICATED_NICKNAME("F-USS002", "이미 사용 중인 닉네임입니다.", HttpStatus.CONFLICT),
    USER_NOT_FOUND("F-US003", "존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND),
    INVALID_INPUT_VALUE("F-US004", "요청값이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_OLD_PASSWORD("F-US005", "기존 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    SAME_PASSWORD("F-US006", "기존 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    PASSWORD_SAME_AS_OLD("F-US007", "새 비밀번호는 기존 비밀번호와 같을 수 없습니다.", HttpStatus.BAD_REQUEST),
    DUPLICATED_PHONE_NUMBER("F-US008", "이미 사용 중인 전화번호입니다.", HttpStatus.CONFLICT),
    USED_USER_INFORMATION("F-US008", "이미 사용 중인 사용자 정보입니다.", HttpStatus.CONFLICT),
    NO_READABLE_BODY("F-US009", "요청 body를 읽을 수 없습니다.", HttpStatus.BAD_REQUEST),

    // post
    POST_NOT_FOUND("F-PO001", "게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    POST_NOT_OWNER("F-PO002", "게시글의 작성자가 아닙니다.", HttpStatus.FORBIDDEN),
    ALREADY_LIKED_POST("F-PO003", "좋아요한 게시글입니다.", HttpStatus.CONFLICT),
    ALREADY_UNLIKED_POST("F-PO004", "좋아요 하지 않은 게시글입니다.", HttpStatus.CONFLICT),

    // post_like
    POST_LIKE_NOT_FOUND("F-PO003", "현재 유저의 해당 게시글 좋아요 기록을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    POST_ALREADY_LIKED("F-PO004", "현재 유저가 해당 게시글을 이미 좋아요 했습니다.", HttpStatus.BAD_REQUEST),
    POST_NOT_LIKED("F-PO005", "현재 유저가 해당 게시글을 좋아요 하지 않았습니다.", HttpStatus.BAD_REQUEST),

    // auth
    INVALID_LOGIN("F-AU001", "로그인 실패하였습니다.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("F-AU002", "인증 실패하였습니다.", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED("F-AU003", "접근 권한이 없습니다.", HttpStatus.FORBIDDEN),

    // jwt (auth)
    INVALID_JWT("F-JT001", "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
    EXPIRED_JWT("F-JT002", "토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    JWT_VALIDATION_ERROR("F-JT003", "JWT 검증 중 예기치 못한 오류가 발생했습니다.", HttpStatus.UNAUTHORIZED),
    EMPTY_JWT("F-JT004", "토큰이 비어있습니다.", HttpStatus.UNAUTHORIZED),
    BLACKLISTED_TOKEN("F-JT005", "로그아웃된 토큰입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_REFRESH_TOKEN("F-JT006", "refresh 토큰이 없거나 유효하지 않습니다.", HttpStatus.BAD_REQUEST),

    // api request
    EXTERNAL_API_ERROR("E-E001", "외부 시스템 오류가 발생했습니다. 잠시 후 다시 시도해주세요.", HttpStatus.BAD_GATEWAY),

    EXTERNAL_API_TIMEOUT("E-E002", "요청 처리 시간이 초과되었습니다. 잠시 후 다시 시도해주세요.", HttpStatus.GATEWAY_TIMEOUT)


}