package com.beotkkotthon.areyousleeping.controller;

import com.beotkkotthon.areyousleeping.annotation.UserId;
import com.beotkkotthon.areyousleeping.dto.global.ResponseDto;
import com.beotkkotthon.areyousleeping.dto.request.TeamSaveDto;
import com.beotkkotthon.areyousleeping.exception.CommonException;
import com.beotkkotthon.areyousleeping.exception.ErrorCode;
import com.beotkkotthon.areyousleeping.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name="Team", description = "팀에 관한 컨트롤러")
public class TeamController {

    private final TeamService teamService;

    // 팀 생성
    @PostMapping("/team")
    @Operation(summary = "팀 생성", description = "팀 생성 API")
    public ResponseDto<?> createTeam(@UserId Long userId, @RequestBody @Valid TeamSaveDto teamSaveDto){
        return ResponseDto.created(teamService.createTeam(userId, teamSaveDto));
    }
    @GetMapping("/team")
    @Operation(summary = "팀 목록 조회", description = "팀 목록 조회 API")
    public ResponseDto<?> getTeams(
            @RequestParam(value="keyword", required = false) String keyword,
            @RequestParam(value="category", required = false) String category,
            @RequestParam(value="isEmpty", required = false) Boolean isEmpty,
            @RequestParam(value="isPublic", required = false) Boolean isPublic,
            @RequestParam(value="page") Integer page,
            @RequestParam(value="size") Integer size
    ) {
        if (page >= 0 && size >= 0) {
            return ResponseDto.ok(teamService.getTeams(page, size, keyword, category, isEmpty, isPublic));
        } else {
            throw new CommonException(ErrorCode.BAD_REQUEST_PARAMETER);
        }
    }

}
