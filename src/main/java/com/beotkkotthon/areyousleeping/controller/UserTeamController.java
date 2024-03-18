package com.beotkkotthon.areyousleeping.controller;

import com.beotkkotthon.areyousleeping.annotation.UserId;
import com.beotkkotthon.areyousleeping.dto.global.ResponseDto;
import com.beotkkotthon.areyousleeping.service.UserTeamService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "UserTeam", description = "유저와 팀간의 관계에 대한 API")
@RequestMapping("/api/v1/user-team")
public class UserTeamController {

    private final UserTeamService userTeamService;

    @PostMapping("/{teamId}")
    public ResponseDto<?> joinTeam(@PathVariable Long teamId, @UserId Long userId){

        userTeamService.joinTeam(teamId, userId);

        return ResponseDto.ok().build();
    }
}
