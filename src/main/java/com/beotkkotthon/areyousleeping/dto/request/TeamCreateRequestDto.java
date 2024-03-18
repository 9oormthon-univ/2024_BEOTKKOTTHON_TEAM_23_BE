package com.beotkkotthon.areyousleeping.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
@Builder
public record TeamCreateRequestDto(
        @JsonProperty("title")
        String title,
        @JsonProperty("max_num")
        Integer maxNum,
        @JsonProperty("target_time")
        Integer targetTime,
        @JsonProperty("isSecret")
        Boolean isSecret,
        @JsonProperty("password")
        String password,
        @JsonProperty("category")
        String category,
        @JsonProperty("description")
        String description
) {
}
