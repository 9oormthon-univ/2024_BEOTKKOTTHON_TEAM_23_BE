package com.beotkkotthon.areyousleeping.controller;

import com.beotkkotthon.areyousleeping.dto.global.ResponseDto;
import com.beotkkotthon.areyousleeping.service.LikeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/like")
@Tag(name="Like", description = "좋아요 관련 API")
public class LikeController {
    private final LikeService likeService;
    @PostMapping("")
    public ResponseDto<?> toggleLike(Long userId, Long postId) {
        return ResponseDto.ok(likeService.toggleLike(userId, postId));
    }
    @GetMapping("/post")
    public ResponseDto<?> getLikePost(Long userId) {
        return ResponseDto.ok(likeService.getLikedPost(userId));
    }
}
