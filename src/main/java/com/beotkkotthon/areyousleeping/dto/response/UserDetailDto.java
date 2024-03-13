package com.beotkkotthon.areyousleeping.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.beotkkotthon.areyousleeping.domain.User;
import com.beotkkotthon.areyousleeping.dto.type.EProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserDetailDto(
        @Schema(description = "유저 ID", example = "1")
        @NotNull(message = "유저 ID가 없습니다.")
        Long userId,

        @Schema(description = "닉네임", example = "개똥이")
        @NotNull(message = "닉네임이 없습니다.")
        String nickname,

        @Schema(description = "로그인 제공자", example = "KAKAO, GOOGLE, APPLE, DEFAULT")
        @NotNull(message = "로그인 제공자가 없습니다.")
        EProvider provider,

        @JsonProperty("profile_image_url") @Schema(description = "프로필 이미지 URL", example = "https://emodiary.s3.ap-northeast-2.amazonaws.com/profile/1.png")
        @NotNull(message = "프로필 이미지가 없습니다.")
        String profileImageUrl
) {
        public static UserDetailDto fromEntity(User user) {
            return UserDetailDto.builder()
                    .userId(user.getUserId())
                    .nickname(user.getNickname())
                    .provider(user.getProvider())
                    .profileImageUrl(user.getProfileImageUrl())
                    .build();
        }
}
