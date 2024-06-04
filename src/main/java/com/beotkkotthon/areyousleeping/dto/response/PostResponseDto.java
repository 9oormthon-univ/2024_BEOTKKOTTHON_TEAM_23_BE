package com.beotkkotthon.areyousleeping.dto.response;

import com.beotkkotthon.areyousleeping.domain.Post;
import com.beotkkotthon.areyousleeping.domain.User;
import lombok.Builder;

@Builder
public record PostResponseDto(
        String postTitle,
        String postContent,
        User user,
        String createdAt,
        String updatedAt
) {
    public static PostResponseDto fromEntity(Post post) {
        return PostResponseDto.builder()
                .postTitle(post.getPostTitle())
                .postContent(post.getPostContent())
                .user(post.getUser())
                .createdAt(post.getCreatedAt().toString())
                .updatedAt(post.getUpdatedAt().toString())
                .build();
    }
}
