package com.beotkkotthon.areyousleeping.controller;

import com.beotkkotthon.areyousleeping.annotation.UserId;
import com.beotkkotthon.areyousleeping.dto.global.ResponseDto;
import com.beotkkotthon.areyousleeping.dto.request.MissionImageDto;
import com.beotkkotthon.areyousleeping.dto.request.MissionTextDto;
import com.beotkkotthon.areyousleeping.service.MissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-team/{teamId}/mission")
@Tag(name = "Mission", description = "특정 팀(teamId)에 대한 미션 관련 API")
public class MissionController {
    private final MissionService missionService;

    @PatchMapping(value = "/pic", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "사진 미션에 대한 결과 업데이트", description = "사진 미션에 대한 결과를 업데이트합니다.")
    public ResponseDto<?> updateMissionResultPic(
            @UserId Long userId,
            @PathVariable Long teamId,
            @RequestPart(value = "message", required = true)
            MissionImageDto missionImageDto,
            @RequestPart(value = "file", required = false)
            MultipartFile imageFile
    ){
        missionService.updateMissionResultPic(userId, teamId, missionImageDto, imageFile);
        return ResponseDto.ok(null);
    }

    @PatchMapping("/text")
    @Operation(summary = "질문 미션에 대한 결과 업데이트", description = "질문 미션에 대한 결과를 업데이트합니다.")
    public ResponseDto<?> updateMissionResultText(
            @UserId Long userId,
            @PathVariable Long teamId,
            @RequestBody MissionTextDto missionTextDto
    ){
        missionService.updateMissionResultText(userId, teamId, missionTextDto);
        return ResponseDto.ok(null);
    }

    @GetMapping("")
    @Operation(summary = "미션 타임라인 조회", description = "팀에 속한 팀원들의 미션 타임라인을 조회합니다.")
    public ResponseDto<?> getMissionTimeline(
            @PathVariable Long teamId
    ){
        return ResponseDto.ok(missionService.getMissionTimeline(teamId));
    }

}
