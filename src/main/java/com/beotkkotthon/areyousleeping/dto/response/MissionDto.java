package com.beotkkotthon.areyousleeping.dto.response;

import com.beotkkotthon.areyousleeping.domain.Mission;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
@Schema(name = "MissionDto", description = "발행된 미션과 관련된 유저 정보")
public record MissionDto(
        Long missionId,
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
                .missionId(mission.getId())
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