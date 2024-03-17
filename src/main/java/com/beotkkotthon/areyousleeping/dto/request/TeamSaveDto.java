package com.beotkkotthon.areyousleeping.dto.request;

import com.beotkkotthon.areyousleeping.domain.Team;
import com.beotkkotthon.areyousleeping.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "팀 생성 DTO")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TeamSaveDto {

    @Schema(description = "방 제목")
    private String title;

    @Schema(description = "방 최대인원")
    private int maxNum;

    @Schema(description = "방 밤샘 설정시간")
    private Integer targetTime;

    @Schema(description = "방 공개/비공개 여부")
    private boolean isSecret;

    @Schema(description = "방 비밀번호")
    private String password;

    @Schema(description = "방 카테고리")
    private String category;

    @Schema(description = "팀 소개 문구")
    private String description;

    public Team toEntity(){
        return Team.builder()
                .title(title)
                .maxNum(maxNum)
                .targetTime(targetTime)
                .isSecret(isSecret)
                .password(password)
                .category(category)
                .description(description)
                .build();
    }
}
