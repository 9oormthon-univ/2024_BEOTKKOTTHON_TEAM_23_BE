package com.beotkkotthon.areyousleeping.dto.response;

import com.beotkkotthon.areyousleeping.domain.UserTeam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Schema(description="유저가 팀 참여시 응답 DTO")
@Builder
public record UserTeamResponseDto(
        Long userId,
        Long teamId,
        Long historyTeamId,
        Boolean isActive,
        Boolean isLeader,
        LocalDateTime lastActiveAt,
        LocalDateTime createdAt,
        int currentNum
) {
}
