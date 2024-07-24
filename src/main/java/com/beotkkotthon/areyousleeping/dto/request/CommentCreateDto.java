package com.beotkkotthon.areyousleeping.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CommentCreateDto(
        @JsonProperty(value = "content", required = true)
        String commentContent,
        @JsonProperty(value = "postId", required = true)
        Long postId
) {
}
