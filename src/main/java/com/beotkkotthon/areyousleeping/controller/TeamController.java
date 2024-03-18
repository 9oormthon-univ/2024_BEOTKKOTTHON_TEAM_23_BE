package com.beotkkotthon.areyousleeping.controller;

import com.beotkkotthon.areyousleeping.annotation.UserId;
import com.beotkkotthon.areyousleeping.domain.Team;
import com.beotkkotthon.areyousleeping.dto.global.ResponseDto;
import com.beotkkotthon.areyousleeping.dto.request.TeamSaveDto;
import com.beotkkotthon.areyousleeping.dto.response.TeamResponseDto;
import com.beotkkotthon.areyousleeping.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TeamController {

    private final TeamService teamService;

    // 팀 생성
    @PostMapping("/team")
    @Operation(summary = "팀 생성", description = "팀 생성 API")
    public ResponseDto<?> createTeam(@UserId Long userId, @RequestBody @Valid TeamSaveDto teamSaveDto){

        Team createdTeam=teamService.createTeam(userId, teamSaveDto);
        TeamResponseDto teamResponseDto = new TeamResponseDto(createdTeam);

        return ResponseDto.ok(teamResponseDto);
    }

}
