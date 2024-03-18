package com.beotkkotthon.areyousleeping.dto.response;

import com.beotkkotthon.areyousleeping.domain.UserTeam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description="유저가 팀 참여시 응답 DTO")
public class UserTeamResponseDto {

    private Long userId;
    private Long teamId;
    private Boolean isActive;
    private Boolean isLeader;
    private LocalDateTime lastActiveAt;
    private LocalDateTime createdAt;

    @Builder
    public UserTeamResponseDto(UserTeam userTeam){
        this.userId = userTeam.getUser().getId();
        this.teamId = userTeam.getTeam().getId();
        this.isActive = userTeam.getIsActive();
        this.isLeader = userTeam.getIsActive();
        this.lastActiveAt = userTeam.getLastActiveAt();
        this.createdAt=userTeam.getCreatedAt();
    }
}
