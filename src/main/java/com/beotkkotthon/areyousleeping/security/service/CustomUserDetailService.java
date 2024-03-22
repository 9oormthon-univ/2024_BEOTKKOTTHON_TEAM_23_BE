package com.beotkkotthon.areyousleeping.security.service;

import com.beotkkotthon.areyousleeping.exception.CommonException;
import com.beotkkotthon.areyousleeping.exception.ErrorCode;
import com.beotkkotthon.areyousleeping.repository.UserRepository;
import com.beotkkotthon.areyousleeping.security.info.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRepository.UserSecurityForm securityForm = userRepository.findSecurityFormBySerialId(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 아이디입니다."));
        return UserPrincipal.create(securityForm);
    }
    public UserPrincipal loadUserById(Long id) {
        UserRepository.UserSecurityForm userSecurityForm = userRepository.findSecurityFormById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        return UserPrincipal.create(userSecurityForm);
    }
}