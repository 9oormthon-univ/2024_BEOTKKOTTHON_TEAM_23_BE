package com.beotkkotthon.areyousleeping.security.info;

import com.beotkkotthon.areyousleeping.dto.type.ERole;

public record JwtUserInfo(Long userId, ERole role) {
}
