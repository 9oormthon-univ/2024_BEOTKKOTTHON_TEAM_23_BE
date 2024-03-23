package com.beotkkotthon.areyousleeping.dto.response;

import com.beotkkotthon.areyousleeping.domain.AllNighters;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record AllNightersDto(
    Long historyTeamId, // historyTeamId
    LocalDateTime startAt,
    LocalDateTime endAt,
    Integer duration
) {
    public static AllNightersDto fromEntity(AllNighters allNighters) {
        return AllNightersDto.builder()
                .historyTeamId(allNighters.getUserTeam().getId())
                .startAt(allNighters.getStartAt())
                .endAt(allNighters.getEndAt())
                .duration(allNighters.getDuration())
                .build();
    }
}