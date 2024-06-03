package com.beotkkotthon.areyousleeping.dto.response;

import com.beotkkotthon.areyousleeping.domain.Comment;
import lombok.Builder;

@Builder
public record CommentResponseDto(
        Long commentId,
        String commentContent,
        String createdAt,
        String updatedAt
) {
    public static CommentResponseDto fromEntity(Comment comment) {
        return CommentResponseDto.builder()
                .commentId(comment.getId())
                .commentContent(comment.getCommentContent())
                .createdAt(comment.getCreatedAt().toString())
                .updatedAt(comment.getUpdatedAt().toString())
                .build();
    }
}
