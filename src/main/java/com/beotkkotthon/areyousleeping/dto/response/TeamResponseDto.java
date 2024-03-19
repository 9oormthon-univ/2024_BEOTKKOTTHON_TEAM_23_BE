package com.beotkkotthon.areyousleeping.dto.response;

import com.beotkkotthon.areyousleeping.domain.Team;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Schema(description="팀 생성 응답 DTO")
public class TeamResponseDto {

    private Long id;
    private String title;
    private int maxNum;
    private int currentNum;
    private int targetTime;
    private boolean isSecret;
    private String password;
    private String category;
    private String description;
    private List<Object> createdAt;

    @Builder
    public TeamResponseDto(Team team) {
        this.id = team.getId();
        this.title = team.getTitle();
        this.maxNum = team.getMaxNum();
        this.currentNum = team.getCurrentNum();
        this.targetTime = team.getTargetTime();
        this.isSecret = team.getIsSecret();
        this.password = team.getPassword();
        this.category = team.getCategory();
        this.description = team.getDescription();
        this.createdAt = Arrays.asList(
                team.getCreatedAt().getYear(),
                team.getCreatedAt().getMonthValue(),
                team.getCreatedAt().getDayOfMonth(),
                team.getCreatedAt().getHour(),
                team.getCreatedAt().getMinute(),
                team.getCreatedAt().getSecond(),
                team.getCreatedAt().getNano()
        );
    }
}
