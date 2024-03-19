package com.beotkkotthon.areyousleeping.controller;

import com.beotkkotthon.areyousleeping.annotation.UserId;
import com.beotkkotthon.areyousleeping.constants.Constants;
import com.beotkkotthon.areyousleeping.dto.global.ResponseDto;
import com.beotkkotthon.areyousleeping.dto.request.AuthSignUpDto;
import com.beotkkotthon.areyousleeping.dto.request.OauthSignUpDto;
import com.beotkkotthon.areyousleeping.dto.response.JwtTokenDto;
import com.beotkkotthon.areyousleeping.exception.CommonException;
import com.beotkkotthon.areyousleeping.exception.ErrorCode;
import com.beotkkotthon.areyousleeping.service.AuthService;
import com.beotkkotthon.areyousleeping.utility.CookieUtil;
import com.beotkkotthon.areyousleeping.utility.HeaderUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Auth", description = "인증 관련 API")
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/auth/email-duplicate")
    @Operation(summary = "이메일 중복 확인", description = "이메일 중복을 확인합니다.")
    public ResponseDto<?> checkDuplicate(
            @RequestParam(value = "email") String email
    ) {
        return ResponseDto.ok(authService.checkDuplicate(email));
    }

    @PostMapping("/auth/sign-up")
    @Operation(summary = "Default 회원가입", description = "Default 회원가입을 진행합니다.")
    public ResponseDto<?> signUp(
            @RequestBody @Valid AuthSignUpDto authSignUpDto
            ) {
        authService.signUp(authSignUpDto);

        return ResponseDto.ok(null);
    }
    @PostMapping("/oauth2/sign-up")
    @Operation(summary = "OAuth 회원가입", description = "OAuth 회원가입을 진행합니다.")
    public ResponseDto<?> signUp(@UserId Long userId, @RequestBody OauthSignUpDto oauthSignUpDto){
        authService.signUp(userId, oauthSignUpDto);
        return ResponseDto.ok(null);
    }

    @PostMapping("/auth/reissue")
    @Operation(summary = "Access 토큰 재발급", description = "Access 토큰을 재발급합니다.")
    public ResponseDto<?> reissue(
            HttpServletRequest request,
            HttpServletResponse response,
            @UserId Long userId) {
        String refreshToken = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new CommonException(ErrorCode.MISSING_REQUEST_HEADER));

        JwtTokenDto jwtTokenDto = authService.reissue(userId, refreshToken);

        return ResponseDto.ok(jwtTokenDto);
    }

}
