package com.beotkkotthon.areyousleeping.dto.response;

import com.beotkkotthon.areyousleeping.domain.AchievementRate;
import lombok.Builder;

@Builder
public record AchievementRateDto(
        Integer allnightCount,
        Long allnightTotal,
        Integer idealAllnightCount,
        Long leaderAllnightTotal,
        Integer aloneAllnightCount,
        Integer continuousAllnightCount
) {
    public static AchievementRateDto fromEntity(AchievementRate achievementRate) {
        return AchievementRateDto.builder()
                .allnightCount(achievementRate.getAllnightCount())
                .allnightTotal(achievementRate.getAllnightTotal())
                .idealAllnightCount(achievementRate.getIdealAllnightCount())
                .leaderAllnightTotal(achievementRate.getLeaderAllnightTotal())
                .aloneAllnightCount(achievementRate.getAloneAllnightCount())
                .continuousAllnightCount(achievementRate.getContinuousAllnightCount())
                .build();
    }
}