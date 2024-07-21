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
    public static CommentResponseDto fromEntity(Comment comment, User user) {
        return CommentResponseDto.builder()
                .commentId(comment.getId())
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .commentContent(comment.getCommentContent())
                .createdAt(comment.getCreatedAt().toString())
                .updatedAt(comment.getUpdatedAt().toString())
                .build();
    }
    public static List<CommentResponseDto> fromEntities(List<Comment> comments, List<User> users) {
        return comments.stream()
                .map(comment -> CommentResponseDto.fromEntity(comment, users.get(comments.indexOf(comment))))
                .toList();
    }

}
