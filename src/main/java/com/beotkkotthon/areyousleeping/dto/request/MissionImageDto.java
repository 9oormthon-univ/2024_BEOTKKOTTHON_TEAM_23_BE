package com.beotkkotthon.areyousleeping.dto.request;

import com.beotkkotthon.areyousleeping.domain.Mission;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record MissionDto(
        @JsonProperty("user_team_id")
        Long userTeamId,
        @JsonProperty("issued_at")
        LocalDateTime issuedAt,
        @JsonProperty("result")
        Boolean result,
        @JsonProperty("result_image_url")
        String resultImageUrl,
        @JsonProperty("result_text")
        String resultText
) {
    public static MissionDto fromEntity(Mission mission) {
        return MissionDto.builder()
                .userTeamId(mission.getUserTeam().getId())
                .issuedAt(mission.getIssuedAt())
                .result(mission.getResult())
                .resultImageUrl(mission.getResultImageUrl())
                .resultText(mission.getResultText())
                .build();
    }
}