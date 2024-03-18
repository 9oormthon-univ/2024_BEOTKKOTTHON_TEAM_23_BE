package com.beotkkotthon.areyousleeping.controller;

import com.beotkkotthon.areyousleeping.annotation.UserId;
import com.beotkkotthon.areyousleeping.dto.global.ResponseDto;
import com.beotkkotthon.areyousleeping.dto.request.TeamCreateRequestDto;
import com.beotkkotthon.areyousleeping.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @PostMapping("/api/v1/team")
    public ResponseDto<?> createTeam(@UserId Long userId, @RequestBody TeamCreateRequestDto requestDto) {
        return ResponseDto.created(teamService.createTeam(userId, requestDto));
    }

}
