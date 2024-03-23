package com.beotkkotthon.areyousleeping.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description="팔로우 & 언팔로우 시 응답 Dto")
@Builder
public record FollowingResponseDto(
        Long senderId,
        Long receiverId,
        String status
){
}
