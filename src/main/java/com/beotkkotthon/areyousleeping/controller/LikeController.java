package com.beotkkotthon.areyousleeping.controller;

import com.beotkkotthon.areyousleeping.dto.global.ResponseDto;
import com.beotkkotthon.areyousleeping.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LikeController {
    private final LikeService likeService;
    @PostMapping("/api/v1/like")
    public ResponseDto<?> toggleLike(Long userId, Long postId) {
        return ResponseDto.ok(likeService.toggleLike(userId, postId));
    }
    @GetMapping("/api/v1/like/posts")
    public ResponseDto<?> getLikePost(Long userId) {
        return ResponseDto.ok(likeService.getLikedPost(userId));
    }
}
