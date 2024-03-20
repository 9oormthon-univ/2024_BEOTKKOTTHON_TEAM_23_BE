package com.beotkkotthon.areyousleeping.dto.response;

import com.beotkkotthon.areyousleeping.domain.Team;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description="팔로우 & 언팔로우 시 응답 Dto")
public class FollowingResponseDto {

    private Long senderId;
    private Long receiverId;
    private String status;

    @Builder
    public FollowingResponseDto(Long senderId, Long receiverId, String status) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status=status;
    }
}
