package com.beotkkotthon.areyousleeping.security.service;

import com.beotkkotthon.areyousleeping.exception.CommonException;
import com.beotkkotthon.areyousleeping.exception.ErrorCode;
import com.beotkkotthon.areyousleeping.repository.UserRepository;
import com.beotkkotthon.areyousleeping.security.info.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRepository.UserSecurityForm securityForm = userRepository.findSecurityFormBySerialId(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 아이디입니다."));
        log.info("아이디 기반 조회 성공");
        return UserPrincipal.create(securityForm);
    }
    public UserPrincipal loadUserById(Long id) {
        UserRepository.UserSecurityForm userSecurityForm = userRepository.findSecurityFormById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        log.info("user id 기반 조회 성공");

        return UserPrincipal.create(userSecurityForm);
    }
}