package com.beotkkotthon.areyousleeping.dto.request;

import com.beotkkotthon.areyousleeping.dto.type.EProvider;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record OauthLoginDto(
        @NotNull(message = "providerId는 빈값이 될 수 없습니다.")
        @JsonProperty("serialId") @Schema(description = "시리얼 아이디로 삼을 수 있는 사용자 ID를 담는다.", example = "203912941")
        String serialId,
        @NotNull(message = "provider는 빈값이 될 수 없습니다.")
        @JsonProperty("provider") @Schema(description = "프로바이더(소셜로그인 제공자)", example = "KAKAO")
        EProvider provider
) {
}
