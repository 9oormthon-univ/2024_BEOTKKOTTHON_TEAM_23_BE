package com.beotkkotthon.areyousleeping.dto.response;

import com.beotkkotthon.areyousleeping.domain.Comment;
import com.beotkkotthon.areyousleeping.domain.User;
import lombok.Builder;

import java.util.List;

@Builder
public record CommentResponseDto(
        Long commentId,
        Long userId,
        String nickname,
        String profileImageUrl,
        String commentContent,
        String createdAt,
        String updatedAt
) {
    public static CommentResponseDto fromEntity(Comment comment) {
        return CommentResponseDto.builder()
                .commentId(comment.getId())
                .userId(comment.getUser().getId())
                .nickname(comment.getUser().getNickname())
                .profileImageUrl(comment.getUser().getProfileImageUrl())
                .commentContent(comment.getCommentContent())
                .createdAt(comment.getCreatedAt().toString())
                .updatedAt(comment.getUpdatedAt().toString())
                .build();
    }
    public static List<CommentResponseDto> fromEntities(List<Comment> comments) {
        return comments.stream()
                .map(CommentResponseDto::fromEntity)
                .toList();
    }

}
