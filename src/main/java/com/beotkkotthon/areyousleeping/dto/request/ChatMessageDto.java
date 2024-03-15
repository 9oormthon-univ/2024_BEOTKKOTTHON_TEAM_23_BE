package com.beotkkotthon.areyousleeping.dto.request;

import com.beotkkotthon.areyousleeping.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ChatMessageDto(
        @JsonProperty("type")
        String type,
        @JsonProperty("content")
        String content,
        @JsonProperty("sendTime")
        String sendTime) {
}
