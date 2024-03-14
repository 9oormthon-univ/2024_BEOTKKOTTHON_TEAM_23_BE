package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.domain.User;
import com.beotkkotthon.areyousleeping.dto.request.AuthSignUpDto;
import com.beotkkotthon.areyousleeping.dto.request.OauthSignUpDto;
import com.beotkkotthon.areyousleeping.dto.response.JwtTokenDto;
import com.beotkkotthon.areyousleeping.exception.CommonException;
import com.beotkkotthon.areyousleeping.exception.ErrorCode;
import com.beotkkotthon.areyousleeping.repository.UserRepository;
import com.beotkkotthon.areyousleeping.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signUp(AuthSignUpDto authSignUpDto) {
        userRepository.save(
                User.signUp(authSignUpDto, bCryptPasswordEncoder.encode(authSignUpDto.password()))
        );
    }
    @Transactional
    public void signUp(Long userId, OauthSignUpDto oauthSignUpDto){
        User oauthUser = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        oauthUser.register(oauthSignUpDto.nickname());
    }
    @Transactional
    public JwtTokenDto reissue(Long userId, String refreshToken) {
        User user = userRepository.findByIdAndRefreshTokenAndIsLogin(userId, refreshToken, true)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_LOGIN_USER));

        JwtTokenDto jwtTokenDto = jwtUtil.generateTokens(user.getId(), user.getRole());
        user.updateRefreshToken(jwtTokenDto.refreshToken());

        return jwtTokenDto;
    }
}
