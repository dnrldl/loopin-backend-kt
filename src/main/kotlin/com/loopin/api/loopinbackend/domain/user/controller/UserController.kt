package com.loopin.api.loopinbackend.domain.user.controller

import com.loopin.api.loopinbackend.common.annotation.AuthUserId
import com.loopin.api.loopinbackend.common.response.SuccessResponse
import com.loopin.api.loopinbackend.common.response.code.SuccessCode
import com.loopin.api.loopinbackend.common.validation.ValidationMessage
import com.loopin.api.loopinbackend.common.validation.ValidationPattern
import com.loopin.api.loopinbackend.domain.user.command.CheckMyPasswordCommand
import com.loopin.api.loopinbackend.domain.user.command.UpdateUserInfoCommand
import com.loopin.api.loopinbackend.domain.user.command.UpdateUserPasswordCommand
import com.loopin.api.loopinbackend.domain.user.mapper.toInfoView
import com.loopin.api.loopinbackend.domain.user.query.CheckMyPasswordResult
import com.loopin.api.loopinbackend.domain.user.query.CheckUserAvailableResult
import com.loopin.api.loopinbackend.domain.user.query.UserInfoView
import com.loopin.api.loopinbackend.domain.user.service.UserCommandService
import com.loopin.api.loopinbackend.domain.user.service.UserQueryService
import com.loopin.api.loopinbackend.domain.user.web.req.CheckMyPasswordRequest
import com.loopin.api.loopinbackend.domain.user.web.req.RegisterUserRequest
import com.loopin.api.loopinbackend.domain.user.web.req.UpdateUserInfoRequest
import com.loopin.api.loopinbackend.domain.user.web.req.UpdateUserPasswordRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Tag(name = "User", description = "유저 API")
@RestController
@RequestMapping("/api/users")
@Validated
class UserController(
    private val userQueryService: UserQueryService,
    private val userCommandService: UserCommandService
) {
    @Operation(summary = "회원가입")
    @PostMapping("/register")
    fun register(@RequestBody @Valid body: RegisterUserRequest): SuccessResponse<Long> =
        SuccessResponse.of(userCommandService.register(body), SuccessCode.SAVE_SUCCESS)

    @Operation(summary = "유저 정보 조회")
    @GetMapping("/{id}")
    fun getUserInfo(@PathVariable id: Long): SuccessResponse<UserInfoView> =
        SuccessResponse.of(userQueryService.getUserById(id).toInfoView(), SuccessCode.RETRIEVE_SUCCESS)

    @Operation(summary = "이메일 유효성 조회")
    @GetMapping("/check/email")
    fun checkEmail(@RequestParam @Email @NotBlank email: String): SuccessResponse<CheckUserAvailableResult> =
        SuccessResponse.of(
            CheckUserAvailableResult(!userQueryService.existsByEmail(email)),
            SuccessCode.RETRIEVE_SUCCESS
        )

    @Operation(summary = "닉네임 유효성 조회")
    @GetMapping("/check/nickname")
    fun checkNickname(
        @RequestParam @NotBlank(message = ValidationMessage.NICKNAME_NOT_BLANK)
        @Size(
            min = ValidationPattern.NICKNAME_MIN_LENGTH,
            max = ValidationPattern.NICKNAME_MAX_LENGTH,
            message = ValidationMessage.NICKNAME_SIZE
        ) nickname: String
    ): SuccessResponse<CheckUserAvailableResult> =
        SuccessResponse.of(
            CheckUserAvailableResult(!userQueryService.existsByNickname(nickname)),
            SuccessCode.RETRIEVE_SUCCESS
        )

    @Operation(summary = "전화번호 유효성 조회")
    @GetMapping("/check/phone-number")
    fun checkPhoneNumber(
        @RequestParam @NotBlank(message = ValidationMessage.PHONE_NUMBER_NOT_BLANK)
        @Pattern(
            regexp = ValidationPattern.PHONE_NUMBER_VALID,
            message = ValidationMessage.PHONE_NUMBER_PATTERN
        ) phoneNumber: String
    ): SuccessResponse<CheckUserAvailableResult> =
        SuccessResponse.of(
            CheckUserAvailableResult(!userQueryService.existsByPhoneNumber(phoneNumber)),
            SuccessCode.RETRIEVE_SUCCESS
        )

    @Operation(summary = "현재 비밀번호 확인")
    @PostMapping("/check/my-password")
    fun checkMyPassword(
        @AuthUserId userId: Long,
        @RequestBody body: CheckMyPasswordRequest): SuccessResponse<CheckMyPasswordResult> {
        val command = CheckMyPasswordCommand(userId, body.password)
        val result = CheckMyPasswordResult(userQueryService.checkMyPassword(command))

        return SuccessResponse.of(result, SuccessCode.RETRIEVE_SUCCESS)
    }


    @Operation(summary = "비밀번호 변경")
    @PutMapping("/update-password")
    fun updatePassword(
        @AuthUserId userId: Long,
        @RequestBody body: UpdateUserPasswordRequest
    ): SuccessResponse<Unit> {
        val command = UpdateUserPasswordCommand(
            oldPassword = body.oldPassword,
            newPassword = body.newPassword,
            userId = userId
        )
        userCommandService.updatePassword(command)

        return SuccessResponse.of(SuccessCode.UPDATE_SUCCESS)
    }

    @Operation(summary = "유저 정보 변경")
    @PutMapping("/update")
    fun updateUserInfo(
        @AuthUserId userId: Long,
        @RequestBody body: UpdateUserInfoRequest
    ): SuccessResponse<Unit> {
        val command = UpdateUserInfoCommand(
            userId = userId,
            nickname = body.nickname,
            bio = body.bio
        )
        userCommandService.updateUserInfo(command)

        return SuccessResponse.of(SuccessCode.UPDATE_SUCCESS)
    }

    @Operation(summary = "회원탈퇴")
    @DeleteMapping
    fun withdraw(@AuthUserId userId: Long): SuccessResponse<Unit> {
        userCommandService.withdraw(userId)
        return SuccessResponse.of(SuccessCode.DELETE_SUCCESS)
    }
}