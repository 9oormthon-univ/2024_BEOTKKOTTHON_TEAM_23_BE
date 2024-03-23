package com.beotkkotthon.areyousleeping.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
@Builder
@Schema(name = "ChatMessageDto", description = "채팅 메시지 하나하나")
public record ChatMessageDto(
        @JsonProperty("type")
        String type,
        @JsonProperty("content")
        String content,
        @JsonProperty("sendTime")
        String sendTime,
        @JsonProperty("sender")
        String sender)
{
}
