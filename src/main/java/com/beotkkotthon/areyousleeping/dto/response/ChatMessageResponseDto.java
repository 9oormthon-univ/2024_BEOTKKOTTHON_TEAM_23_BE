package com.beotkkotthon.areyousleeping.dto.response;

import lombok.Builder;

@Builder
public record ChatMessageResponseDto(
        String type,
        String senderNickname,
        String senderProfileImage,
        String content,
        String sendTime) {
}
