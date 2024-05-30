package com.beotkkotthon.areyousleeping.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(description = "유저 신고 요청 DTO")
public record UserReportRequestDto(
        //신고할 유저 아이디
        @JsonProperty("reportedUserId")
        Long reportedUserId,

        //신고 사유
        @JsonProperty("reportContent")
        String content
) {
}
