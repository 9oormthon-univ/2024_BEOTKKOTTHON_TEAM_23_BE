package com.beotkkotthon.areyousleeping.dto.response;

import com.beotkkotthon.areyousleeping.domain.Mission;
import com.beotkkotthon.areyousleeping.domain.User;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record MissionDto(
        Long id,
        Long userTeamId,
        Long userId,
        String nickname,
        String profileImageUrl,
        String content,
        LocalDateTime issuedAt,
        Boolean result,
        String resultImageUrl,
        String resultText
) {

    public static MissionDto fromEntity(Mission mission) {
        return MissionDto.builder()
                .id(mission.getId())
                .userTeamId(mission.getUserTeam().getId())
                .userId(mission.getUserTeam().getUser().getId())
                .nickname(mission.getUserTeam().getUser().getNickname())
                .profileImageUrl(mission.getUserTeam().getUser().getProfileImageUrl())
                .content(mission.getContent())
                .issuedAt(mission.getIssuedAt())
                .result(mission.getResult())
                .resultImageUrl(mission.getResultImageUrl())
                .resultText(mission.getResultText())
                .build(
        );
    }
}