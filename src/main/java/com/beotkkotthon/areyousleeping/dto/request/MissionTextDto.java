package com.beotkkotthon.areyousleeping.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
@Builder
@Schema(name = "MissionTextDto", description = "질문 미션 결과 업로드")
public record MissionTextDto(
        @JsonProperty("result")
        Boolean result,
        @JsonProperty("result_text")
        String resultText
) {
}

