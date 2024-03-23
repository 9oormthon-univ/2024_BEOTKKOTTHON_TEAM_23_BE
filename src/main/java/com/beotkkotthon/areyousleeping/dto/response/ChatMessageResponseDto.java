package com.beotkkotthon.areyousleeping.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "ChatMessageResponseDto", description = "채팅 메시지 하나하나의 응답 Dto")
public record ChatMessageResponseDto(
        String type,
        String senderNickname,
        String senderProfileImage,
        String content,
        String sendTime) {
}
