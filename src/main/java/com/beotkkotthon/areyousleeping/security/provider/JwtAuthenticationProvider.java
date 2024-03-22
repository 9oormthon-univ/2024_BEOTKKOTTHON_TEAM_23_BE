package com.beotkkotthon.areyousleeping.security.provider;

import com.beotkkotthon.areyousleeping.security.info.JwtUserInfo;
import com.beotkkotthon.areyousleeping.security.info.UserPrincipal;
import com.beotkkotthon.areyousleeping.security.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailService customUserDetailService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.getPrincipal().getClass().equals(String.class)){
            // form login 요청인 경우
            return authOfBeforeLogin(authentication.getPrincipal().toString());

        } else { // 로그인 이후의 경우에 String이 아닌 복합 정보가 들어오는 경우
            return authOfAfterLogin((JwtUserInfo) authentication.getPrincipal());
        }
    }
    private Authentication authOfBeforeLogin(String serialId){
        UserPrincipal userPrincipal = customUserDetailService.loadUserByUsername(serialId);
        return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
    }
    private Authentication authOfAfterLogin(JwtUserInfo userInfo){
        UserPrincipal userPrincipal = customUserDetailService.loadUserById(userInfo.userId());
        return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}