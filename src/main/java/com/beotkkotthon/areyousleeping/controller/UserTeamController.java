package com.beotkkotthon.areyousleeping.controller;

import com.beotkkotthon.areyousleeping.annotation.UserId;
import com.beotkkotthon.areyousleeping.domain.UserTeam;
import com.beotkkotthon.areyousleeping.dto.global.ResponseDto;
import com.beotkkotthon.areyousleeping.dto.response.TeamMemberInfoDto;
import com.beotkkotthon.areyousleeping.dto.response.UserTeamResponseDto;
import com.beotkkotthon.areyousleeping.exception.CommonException;
import com.beotkkotthon.areyousleeping.exception.ErrorCode;

import com.beotkkotthon.areyousleeping.service.UserTeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "UserTeam", description = "유저와 팀간의 관계에 대한 API")
@RequestMapping("/api/v1/user-team/{teamId}")
public class UserTeamController {

    private final UserTeamService userTeamService;
    private static final Logger logger = LoggerFactory.getLogger(UserTeamController.class);

    @PostMapping("")
    @Operation(summary = "팀 참여하기", description = "유저가 팀 참여하기 버튼을 눌러 팀에 참여합니다.")
    public ResponseDto<?> joinTeam(@PathVariable Long teamId, @UserId Long userId){

        UserTeam userTeam = userTeamService.joinTeam(teamId, userId);
        UserTeamResponseDto userTeamResponseDto = new UserTeamResponseDto(userTeam);

        return ResponseDto.ok(userTeamResponseDto);
    }

    @DeleteMapping("")
    @Operation(summary = "팀 나가기", description = "유저가 팀 나가기 버튼을 눌러 팀에서 나갑니다.")
    public ResponseDto<?> leaveTeam(@PathVariable Long teamId, @UserId Long userId){

        UserTeam userTeam = userTeamService.leaveTeam(teamId, userId);

        return ResponseDto.ok(null);
    }

    @PatchMapping("")
    @Operation(summary = "밤샘 참여하기/ 밤샘 중단하기", description = "유저가 밤샘 시작하기 버튼을 눌러 밤샘이 활성화되고, 밤샘 그만하기 버튼을 누르면 밤샘이 비활성화됩니다.")
    public ResponseDto<?> updateUserActiveStatus(@PathVariable Long teamId, @RequestParam boolean isActive, @UserId Long userId){

        UserTeam updatedUserTeam= userTeamService.updateUserActiveStatus(teamId, userId, isActive);

        return ResponseDto.ok(updatedUserTeam.getIsActive());
    }

    @GetMapping("/all-night-count")
    @Operation(summary = "밤샘중인 팀원 수 조회", description = "팀 id를 받아 밤샘이 활성화되어 있는 팀원들의 총 숫자를 조회합니다.")
    public ResponseDto<?> getAllNightCounters(@PathVariable Long teamId){
        Long count=userTeamService.getActiveMembersCount(teamId);
        return ResponseDto.ok(count);
    }

    @GetMapping("/mate")
    @Operation(summary = "밤샘메이트 조회", description = "팀 id를 받아 팀원들의 이름, 칭호, 밤샘활성화 여부를 조회합니다.")
    public ResponseDto<?> getTeamMembersInfo(@UserId Long userId, @PathVariable Long teamId){

        // 해당 팀에 속한 모든 유저 조회
        List<TeamMemberInfoDto> teamMembersInfo = userTeamService.getTeamMembersInfo(teamId);

        return ResponseDto.ok(teamMembersInfo);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "팀원 추방하기", description = "팀 id와 user id를 받아 방장이 해당 팀의 특정 유저를 팀에서 추방합니다.")
    public ResponseDto<?> removeTeamMember(@UserId Long leaderId, @PathVariable Long teamId, @PathVariable Long userId) {

        userTeamService.removeTeamMember(leaderId, teamId, userId);
        return ResponseDto.ok(null);

    }
}
