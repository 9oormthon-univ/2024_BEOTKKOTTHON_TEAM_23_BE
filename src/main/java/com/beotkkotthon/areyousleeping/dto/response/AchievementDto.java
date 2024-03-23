package com.beotkkotthon.areyousleeping.dto.response;

import com.beotkkotthon.areyousleeping.domain.Achievement;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
@Schema(name = "AchievementDto", description = "업적 정보")
public record AchievementDto(
        Long id,
        String title,
        String content,
        Integer difficulty,
        String achievementImageUrl,
        LocalDateTime createdAt
) {
    public static AchievementDto fromEntity(Achievement achievement) {
        return AchievementDto.builder()
                .id(achievement.getId())
                .title(achievement.getTitle())
                .content(achievement.getContent())
                .difficulty(achievement.getDifficulty())
                .achievementImageUrl(achievement.getAchievementImageUrl())
                .createdAt(achievement.getCreatedAt())
                .build();
    }
}
