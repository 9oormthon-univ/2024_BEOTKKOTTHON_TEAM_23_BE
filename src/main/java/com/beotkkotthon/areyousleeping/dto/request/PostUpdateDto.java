package com.beotkkotthon.areyousleeping.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PostUpdateDto(
        @JsonProperty("title")
        String title,
        @JsonProperty("content")
        String content
) {
}
