package com.beotkkotthon.areyousleeping.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description="팔로우한 유저들의 정보 조회 Dto")
public class FollowingInfoDto {

    private Long userId;
    private String nickname;
    private String title;

    @Builder
    public FollowingInfoDto(Long userId, String nickname, String title){
        this.userId = userId;
        this.nickname = nickname;
        this.title = title;
    }

}
