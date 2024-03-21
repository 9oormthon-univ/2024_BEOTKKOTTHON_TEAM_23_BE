package com.beotkkotthon.areyousleeping.dto.response;

import com.beotkkotthon.areyousleeping.domain.Team;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
@Schema(description="팀 생성 응답 DTO")
public record TeamResponseDto(
        Long id,
        String title,
        int maxNum,
        int currentNum,
        int targetTime,
        boolean isSecret,
        String password,
        String category,
        String description,
        LocalDateTime createdAt
) {
    public static TeamResponseDto fromEntity(Team team) {
        return TeamResponseDto.builder()
                .id(team.getId())
                .title(team.getTitle())
                .maxNum(team.getMaxNum())
                .currentNum(team.getCurrentNum())
                .targetTime(team.getTargetTime())
                .isSecret(team.getIsSecret())
                .password(team.getPassword())
                .category(team.getCategory())
                .description(team.getDescription())
                .createdAt(team.getCreatedAt())
                .build();
    }
}
