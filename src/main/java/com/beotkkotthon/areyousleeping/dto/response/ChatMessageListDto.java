package com.beotkkotthon.areyousleeping.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ChatMessageListDto (
    List<ChatMessageResponseDto> messageList
){}
