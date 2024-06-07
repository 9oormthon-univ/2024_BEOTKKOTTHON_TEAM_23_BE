package com.beotkkotthon.areyousleeping.dto.response;

import com.beotkkotthon.areyousleeping.domain.Report;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(name = "ReportResponseDto", description = "신고 응답 Dto")
public record UserReportResponseDto(
        @Schema(description = "신고 ID", example = "1")
        @NotNull(message = "신고 ID가 없습니다.")
        Long reportId,

        @Schema(description = "신고자 ID", example = "1")
        @NotNull(message = "신고자 ID가 없습니다.")
        Long reporterUserId,

        @Schema(description = "신고자 닉네임", example = "개똥이")
        @NotNull(message = "신고자 닉네임이 없습니다.")
        String reporterNickname,

        @Schema(description = "신고자 신고 여부", example = "true")
        Boolean reporterIsReported,

        @Schema(description = "신고된 유저 ID", example = "2")
        @NotNull(message = "신고된 유저 ID가 없습니다.")
        Long reportedUserId,

        @Schema(description = "신고된 유저 닉네임", example = "말똥이")
        @NotNull(message = "신고된 유저 닉네임이 없습니다.")
        String reportedUserNickname,

        @Schema(description = "신고된 유저 신고 여부", example = "true")
        Boolean reportedUserIsReported,

        @Schema(description = "신고 내용", example = "부적절한 언행")
        @NotNull(message = "신고 내용이 없습니다.")
        String reportContent
) {
    public static UserReportResponseDto fromEntity(Report report) {
        return UserReportResponseDto.builder()
                .reportId(report.getId())
                .reporterUserId(report.getReporter().getId())
                .reporterNickname(report.getReporter().getNickname())
                .reporterIsReported(report.getReporter().isReported())
                .reportedUserId(report.getReportedUser().getId())
                .reportedUserNickname(report.getReportedUser().getNickname())
                .reportedUserIsReported(report.getReportedUser().isReported())
                .reportContent(report.getReportContent())
                .build();
    }
}

