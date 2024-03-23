package com.beotkkotthon.areyousleeping.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description="팔로우한 유저들의 정보 조회 Dto")
@Builder
public record FollowingInfoDto(
        Long userId,
        String nickname,
        String title
){
}
