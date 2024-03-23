package com.beotkkotthon.areyousleeping.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description="밤샘 메이트 조회 시 응답 dto")
@Builder
public record TeamMemberInfoDto(
        String nickname,
        String title,
        Boolean isActive,
        Boolean isLeader,
        String profileImgUrl
) {
}
