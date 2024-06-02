package com.beotkkotthon.areyousleeping.dto.response;

import com.beotkkotthon.areyousleeping.domain.Board;
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
    public static BoardDto fromEntity(Board board) {
        return BoardDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .user(board.getUser())
                .createdAt(board.getCreatedAt().toString())
                .updatedAt(board.getUpdatedAt().toString())
                .build();
    }
}
