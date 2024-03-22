package com.beotkkotthon.areyousleeping.dto.response;

import com.beotkkotthon.areyousleeping.domain.AllNighters;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record AllNightersDto(
    Long id, // userTeamId
    LocalDateTime startAt,
    LocalDateTime endAt,
    Integer duration
) {
    public static AllNightersDto fromEntity(AllNighters allNighters) {
        return AllNightersDto.builder()
                .id(allNighters.getUserTeam().getId())
                .startAt(allNighters.getStartAt())
                .endAt(allNighters.getEndAt())
                .duration(allNighters.getDuration())
                .build();
    }
}