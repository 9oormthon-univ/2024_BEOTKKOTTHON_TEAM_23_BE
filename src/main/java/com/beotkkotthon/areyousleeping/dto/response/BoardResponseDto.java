package com.beotkkotthon.areyousleeping.dto.response;

import com.beotkkotthon.areyousleeping.domain.Board;
import com.beotkkotthon.areyousleeping.domain.User;
import lombok.Builder;

@Builder
public record BoardResponseDto(
        String title,
        String content,
        User user,
        String createdAt,
        String updatedAt
) {
    public static BoardResponseDto fromEntity(Board board) {
        return BoardResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .user(board.getUser())
                .createdAt(board.getCreatedAt().toString())
                .updatedAt(board.getUpdatedAt().toString())
                .build();
    }
}
