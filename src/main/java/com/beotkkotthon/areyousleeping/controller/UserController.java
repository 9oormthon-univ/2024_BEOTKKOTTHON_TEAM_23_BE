package com.beotkkotthon.areyousleeping.controller;

import com.beotkkotthon.areyousleeping.annotation.UserId;
import com.beotkkotthon.areyousleeping.dto.global.ResponseDto;
import com.beotkkotthon.areyousleeping.dto.request.UserUpdateDto;
import com.beotkkotthon.areyousleeping.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 관련 API")
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping("")
    @Operation(summary = "본인 정보 조회", description = "본인 정보를 조회합니다.")
    public ResponseDto<?> readUsers(
            @UserId Long userId
    ) {
        return ResponseDto.ok(userService.readUser(userId));
    }

    @PatchMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "본인 정보 수정", description = "유저 정보를 수정합니다.")
    public ResponseDto<?> updateUser(
            @UserId Long userId,
            @RequestPart(value = "message", required = false)
            @Valid UserUpdateDto requestDto,
            @RequestPart(value = "file", required = false)
            MultipartFile imgFile) {
        userService.updateUser(userId, requestDto, imgFile);
        return ResponseDto.ok(null);
    }

    @GetMapping("/check-duplicate")
    @Operation(summary = "닉네임 중복 확인", description = "닉네임 중복을 확인합니다.")
    public ResponseDto<?> checkDuplicate(
            @RequestParam(value = "nickname") String nickname
    ) {
        return ResponseDto.ok(userService.checkDuplicate(nickname));
    }

}
