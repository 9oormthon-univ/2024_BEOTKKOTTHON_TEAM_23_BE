package com.beotkkotthon.areyousleeping.controller;

import com.beotkkotthon.areyousleeping.annotation.UserId;
import com.beotkkotthon.areyousleeping.domain.Achievement;
import com.beotkkotthon.areyousleeping.dto.global.ResponseDto;
import com.beotkkotthon.areyousleeping.service.AchievementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name="Achievement", description = "칭호 관련 API")
@RequestMapping("/api/v1/achievement")
public class AchievementController {
    private final AchievementService achievementService;

    @GetMapping
    @Operation(summary = "유저의 칭호 조회", description = "해당 유저의 id를 받아 획득한 칭호 모두를 조회합니다.")
    public ResponseDto<?> getUserAchievement(@UserId Long userId){

        List<Achievement> achievementList = achievementService.getUserAchievements(userId);

        return ResponseDto.ok(achievementList);
    }

}
