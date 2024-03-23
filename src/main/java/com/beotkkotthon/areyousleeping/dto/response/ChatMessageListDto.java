package com.beotkkotthon.areyousleeping.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(name = "ChatMessageListDto", description = "하나의 팀에 대해 조회한 채팅 기록들 Dto")
public record ChatMessageListDto (
    List<ChatMessageResponseDto> messageList,
    Boolean hasNext
){}
