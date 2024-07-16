package com.beotkkotthon.areyousleeping.dto.response;

import com.beotkkotthon.areyousleeping.domain.Comment;
import com.beotkkotthon.areyousleeping.domain.Post;
import com.beotkkotthon.areyousleeping.domain.User;
import lombok.Builder;

import java.util.List;

@Builder
public record PostDetailResponseDto(
        Long postId,
        String postTitle,
        String postContent,
        Long userId,
        String nickname,
        String profileImageUrl,
        String createdAt,
        String updatedAt,
        List<CommentResponseDto> commentsDto
) {
    public static PostDetailResponseDto fromEntity(Post post, List<Comment> comments) {
        return PostDetailResponseDto.builder()
                .postId(post.getId())
                .postTitle(post.getPostTitle())
                .postContent(post.getPostContent())
                .userId(post.getUser().getId())
                .nickname(post.getUser().getNickname())
                .profileImageUrl(post.getUser().getProfileImageUrl())
                .createdAt(post.getCreatedAt().toString())
                .updatedAt(post.getUpdatedAt().toString())
                .commentsDto(CommentResponseDto.fromEntities(comments, List.of(post.getUser())))
                .build();
    }
}
