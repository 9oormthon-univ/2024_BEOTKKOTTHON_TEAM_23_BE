package com.beotkkotthon.areyousleeping.controller;

import com.beotkkotthon.areyousleeping.annotation.UserId;
import com.beotkkotthon.areyousleeping.dto.global.ResponseDto;
import com.beotkkotthon.areyousleeping.dto.request.MissionImageDto;
import com.beotkkotthon.areyousleeping.dto.request.MissionTextDto;
import com.beotkkotthon.areyousleeping.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class MissionController {
    private final MissionService missionService;

    @PatchMapping(value = "/api/v1/user-team/{teamId}/mission/pic", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
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

    @PatchMapping("/api/v1/user-team/{teamId}/mission/text")
    public ResponseDto<?> updateMissionResultText(
            @UserId Long userId,
            @PathVariable Long teamId,
            @RequestBody MissionTextDto missionTextDto
    ){
        missionService.updateMissionResultText(userId, teamId, missionTextDto);
        return ResponseDto.ok(null);
    }

    @GetMapping("/api/v1/user-team/{teamId}/mission")
    public ResponseDto<?> getMissionTimeline(
            @UserId Long userId,
            @PathVariable Long teamId
    ){
        return ResponseDto.ok(missionService.getMissionTimeline(userId, teamId));
    }

}
