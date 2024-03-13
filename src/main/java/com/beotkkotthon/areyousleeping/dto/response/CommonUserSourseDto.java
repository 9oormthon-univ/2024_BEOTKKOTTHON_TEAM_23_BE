package com.beotkkotthon.areyousleeping.dto.response;

import com.beotkkotthon.areyousleeping.domain.User;
import lombok.Builder;

@Builder
public record CommonUserSourseDto(
        String nickname,
        String profileImageUrl
) {
    public static CommonUserSourseDto fromEntity(User user){
        return CommonUserSourseDto.builder()
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }
}
