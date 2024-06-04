package com.beotkkotthon.areyousleeping.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.beotkkotthon.areyousleeping.domain.User;
import com.beotkkotthon.areyousleeping.dto.type.EProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(name = "UserDetailDto", description = "유저 상세 정보 조회 Dto")
public record UserDetailDto(
        @Schema(description = "유저 ID", example = "1")
        @NotNull(message = "유저 ID가 없습니다.")
        Long id,

        @Schema(description = "닉네임", example = "개똥이")
        @NotNull(message = "닉네임이 없습니다.")
        String nickname,

        @Schema(description = "로그인 제공자", example = "KAKAO, GOOGLE, APPLE, DEFAULT")
        @NotNull(message = "로그인 제공자가 없습니다.")
        EProvider provider,

        @Schema(description="칭호", example="잠만보, 올빼미")
        String AchievementTitle,

        @JsonProperty("profile_image_url") @Schema(description = "프로필 이미지 URL", example = "https://emodiary.s3.ap-northeast-2.amazonaws.com/profile/1.png")
        @NotNull(message = "프로필 이미지가 없습니다.")
        String profileImageUrl,

        @Schema(description = "신고 여부", example = "true")
        @NotNull(message = "신고 여부가 없습니다.")
        Boolean isReported,

        @Schema(description="팀에 속해있는지 여부", example="true")
        @NotNull(message = "팀에 속해있는지 여부가 없습니다.")
        Boolean isInTeam,

        @Schema(description="팀 ID", example="1")
        Long teamId

) {
        public static UserDetailDto fromEntity(User user, String title, Boolean isInTeam, Long teamId) {
            return UserDetailDto.builder()
                    .id(user.getId())
                    .nickname(user.getNickname())
                    .provider(user.getProvider())
                    .AchievementTitle(title)
                    .profileImageUrl(user.getProfileImageUrl())
                    .isInTeam(isInTeam)
                    .isReported(user.isReported())
                    .teamId(teamId)
                    .build();
        }
}
