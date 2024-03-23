package com.beotkkotthon.areyousleeping.security.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements AuthenticationManager {
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return jwtAuthenticationProvider.authenticate(authentication);
    }
}
