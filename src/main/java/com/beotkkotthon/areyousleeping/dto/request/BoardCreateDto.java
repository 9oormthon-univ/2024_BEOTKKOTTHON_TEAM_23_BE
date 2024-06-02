package com.beotkkotthon.areyousleeping.dto.request;

import com.beotkkotthon.areyousleeping.domain.User;
import lombok.Builder;

@Builder
public record BoardCreateDto(
        String title,
        String content,
        User user,
        String createdAt,
        String updatedAt
) {
}
