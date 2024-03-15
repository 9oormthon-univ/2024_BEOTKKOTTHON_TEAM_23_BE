package com.beotkkotthon.areyousleeping.domain.nosql;

import com.beotkkotthon.areyousleeping.domain.User;
import com.beotkkotthon.areyousleeping.dto.request.ChatMessageDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Getter
@NoArgsConstructor
public class ChatMessage {
    private String type;
    private User sender;
    private String content;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime date;

    @Builder
    public ChatMessage(String type, User sender, String content, OffsetDateTime date) {
        this.type = type;
        this.sender = sender;
        this.content = content;
        this.date = date;
    }

    public static ChatMessage fromJson(ChatMessageDto requestDto, User user) {
        return ChatMessage.builder()
                .type(requestDto.type())
                .sender(user)
                .content(requestDto.content())
                .date(OffsetDateTime.now())
                .build();
    }
}
