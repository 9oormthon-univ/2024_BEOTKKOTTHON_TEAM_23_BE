package com.beotkkotthon.areyousleeping.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
@Builder
public record MissionImageDto(
        @JsonProperty("result")
        Boolean result
) {
}