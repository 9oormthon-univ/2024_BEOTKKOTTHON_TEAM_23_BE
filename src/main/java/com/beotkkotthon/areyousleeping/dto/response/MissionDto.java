package com.beotkkotthon.areyousleeping.dto.response;

import com.beotkkotthon.areyousleeping.domain.Mission;
import java.time.LocalDateTime;

public record MissionDto(
        Long id,
        Long userTeamId,
        String content,
        LocalDateTime issuedAt,
        Boolean result,
        String resultImageUrl,
        String resultText
) {
    public static MissionDto fromEntity(Mission mission) {
        return new MissionDto(
                mission.getId(),
                mission.getUserTeam().getId(),
                mission.getContent(),
                mission.getIssuedAt(),
                mission.getResult(),
                mission.getResultImageUrl(),
                mission.getResultText()
        );
    }
}