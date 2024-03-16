package com.beotkkotthon.areyousleeping.dto.response;

import lombok.Builder;

@Builder
public record ChatMessageResponseDto(
        String type,
        String sender,
        String content,
        String sendTime) {
}
