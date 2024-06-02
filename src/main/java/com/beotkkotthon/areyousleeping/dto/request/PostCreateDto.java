package com.beotkkotthon.areyousleeping.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record PostCreateDto(
        @JsonProperty("title")
        String title,
        @JsonProperty("content")
        String content,
        @JsonProperty("user_id")
        Long userId
) {
}
