package com.beotkkotthon.areyousleeping.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
@Builder
public record MissionTextDto(
        @JsonProperty("result")
        Boolean result,
        @JsonProperty("result_text")
        String resultText
) {
}

