package com.beotkkotthon.areyousleeping.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
@Schema(name = "AllNightersDto", description = "하루하루 밤샘 기록")
public record AllNightersDto(
    Long historyTeamId, // historyTeamId
    LocalDateTime startAt,
    LocalDateTime endAt,
    Integer duration
) {
}