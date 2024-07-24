package com.beotkkotthon.areyousleeping.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PostUpdateDto(
        @JsonProperty("title")
        String title,
        @JsonProperty("content")
        String content,
        @JsonProperty(value = "photo_ids_to_delete")
        List<Long> photoIdsToDelete
) {
}
