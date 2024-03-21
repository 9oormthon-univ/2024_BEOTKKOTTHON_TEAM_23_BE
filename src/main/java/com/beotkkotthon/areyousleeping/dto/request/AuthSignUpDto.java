package com.beotkkotthon.areyousleeping.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(name = "AuthSignUpDto", description = "회원가입 요청")
public record AuthSignUpDto(
        @JsonProperty("serial_id") @Schema(description = "시리얼 ID", example = "example@example.com")
        @NotNull(message = "serial_id는 null이 될 수 없습니다.")
        @Size(min = 6, max = 254, message = "시리얼 ID는 6~254자리로 입력해주세요.")
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "유효하지 않은 이메일 형식입니다.")
        String serial_id,
        @JsonProperty("password") @Schema(description = "비밀번호", example = "1234567890Aa!")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%]).{10,20}$",
                message = "비밀번호는 대문자 1개 이상, 소문자 1개 이상, 숫자 1개 이상, 특수문자(!, @, #, %, $) 1개 이상으로 구성된 10~20자리 비밀번호로 입력해주세요.")
        @NotNull(message = "password는 null이 될 수 없습니다.")
        String password
) {
}
