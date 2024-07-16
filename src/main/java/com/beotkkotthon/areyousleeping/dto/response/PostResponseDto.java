package com.beotkkotthon.areyousleeping.dto.response;

import com.beotkkotthon.areyousleeping.domain.Like;
import com.beotkkotthon.areyousleeping.domain.Post;
import com.beotkkotthon.areyousleeping.domain.User;
import lombok.Builder;

@Builder
public record PostResponseDto(
        Long postId,
        String postTitle,
        Integer likeCount,
        Long userId,
        String nickname,
        String profileImageUrl,
        String createdAt
) {
    public static PostResponseDto fromEntity(Post post, Integer likeCount) {
        return PostResponseDto.builder()
                .postId(post.getId())
                .postTitle(post.getPostTitle())
                .likeCount(likeCount)
                .userId(post.getUser().getId())
                .nickname(post.getUser().getNickname())
                .profileImageUrl(post.getUser().getProfileImageUrl())
                .createdAt(post.getCreatedAt().toString())
                .build();
    }
}
