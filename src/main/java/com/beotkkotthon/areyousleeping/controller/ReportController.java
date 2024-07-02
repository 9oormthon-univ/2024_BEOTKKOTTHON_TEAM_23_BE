package com.beotkkotthon.areyousleeping.controller;

import com.beotkkotthon.areyousleeping.annotation.UserId;
import com.beotkkotthon.areyousleeping.domain.User;
import com.beotkkotthon.areyousleeping.dto.global.ResponseDto;
import com.beotkkotthon.areyousleeping.dto.request.UserReportRequestDto;
import com.beotkkotthon.areyousleeping.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
@Tag(name = "Report Controller", description = "특정 유저에 대한 신고 관련 API")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("")
    @Operation(summary = "특정 유저에 대한 신고 API", description = "신고자, 신고 당한 유저의 id와 신고 내용을 받아 특정 유저를 신고합니다.")
    public ResponseDto<?> reportUser(
            @UserId Long userId,
            @RequestBody UserReportRequestDto userReportRequestDto) {
        return ResponseDto.ok(reportService.reportUser(userId, userReportRequestDto));
    }

}
