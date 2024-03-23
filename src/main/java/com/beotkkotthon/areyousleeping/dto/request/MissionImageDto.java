package com.beotkkotthon.areyousleeping.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "MissionImageDto", description = "사진 미션 결과 업로드")
public record MissionImageDto(
        @JsonProperty("result")
        Boolean result
) {
}