package com.beotkkotthon.areyousleeping.dto.response;

import com.beotkkotthon.areyousleeping.domain.User;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Builder
public record ChatMessageDto(
        String type,
        String nickname,
        String content,
        String sendTime) {
    public static ChatMessageDto fromEntity(String type, User user, String content, String sendTime) {
        return ChatMessageDto.builder()
                .type(type)
                .nickname(user.getNickname())
                .content(content)
                .sendTime(sendTime)
                .build();
    }
}
