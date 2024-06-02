package com.beotkkotthon.areyousleeping.dto.response;

import com.beotkkotthon.areyousleeping.domain.User;
import lombok.Builder;

@Builder
public record BoardDto(
        String title,
        String content,
        User user,
        String createdAt,
        String updatedAt
) {
}
