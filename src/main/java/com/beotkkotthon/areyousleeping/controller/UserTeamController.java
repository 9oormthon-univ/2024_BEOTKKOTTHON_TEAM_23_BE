package com.beotkkotthon.areyousleeping.controller;

import com.beotkkotthon.areyousleeping.annotation.UserId;
import com.beotkkotthon.areyousleeping.domain.UserTeam;
import com.beotkkotthon.areyousleeping.dto.global.ResponseDto;
import com.beotkkotthon.areyousleeping.dto.response.UserActiveStatusResponseDto;
import com.beotkkotthon.areyousleeping.dto.response.UserTeamResponseDto;
import com.beotkkotthon.areyousleeping.dto.request.UserActiveStatusDto;
import com.beotkkotthon.areyousleeping.service.UserTeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "UserTeam", description = "유저와 팀간의 관계에 대한 API")
@RequestMapping("/api/v1/user-team")
public class UserTeamController {

    private final UserTeamService userTeamService;

    @PostMapping("/{teamId}")
    @Operation(summary = "팀 참여하기", description = "유저가 팀 참여하기 버튼을 눌러 팀에 참여합니다.")
    public ResponseDto<?> joinTeam(@PathVariable Long teamId, @UserId Long userId){

        UserTeam userTeam = userTeamService.joinTeam(teamId, userId);
        UserTeamResponseDto userTeamResponseDto = new UserTeamResponseDto(userTeam);

        return ResponseDto.ok(userTeamResponseDto);
    }

    @DeleteMapping("/{teamId}")
    @Operation(summary = "팀 나가기", description = "유저가 팀 나가기 버튼을 눌러 팀에서 나갑니다.")
    public ResponseDto<?> leaveTeam(@PathVariable Long teamId, @UserId Long userId){

        UserTeam userTeam = userTeamService.leaveTeam(teamId, userId);

        return ResponseDto.ok(null);
    }

    @PatchMapping("/{teamId}")
    @Operation(summary = "밤샘 참여하기/ 밤샘 중단하기", description = "유저가 밤샘 참여하기 버튼을 눌러 밤샘 상태가 활성화되고, 밤샘 중단하기 버튼을 누르면 밤샘 상태가 비활성화됩니다.")
    public ResponseDto<?> updateUserActiveStatus(@PathVariable Long teamId, @RequestBody UserActiveStatusDto request, @UserId Long userId){

        UserTeam updatedUserTeam= userTeamService.updateUserActiveStatus(teamId, userId, request.isActive());
        UserActiveStatusResponseDto responseDto = new UserActiveStatusResponseDto();

        return ResponseDto.ok(responseDto);
    }

}
