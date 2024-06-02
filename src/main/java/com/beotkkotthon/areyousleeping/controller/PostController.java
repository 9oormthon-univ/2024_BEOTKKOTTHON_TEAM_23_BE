package com.beotkkotthon.areyousleeping.controller;

import com.beotkkotthon.areyousleeping.dto.global.ResponseDto;
import com.beotkkotthon.areyousleeping.dto.request.PostCreateDto;
import com.beotkkotthon.areyousleeping.dto.request.PostUpdateDto;
import com.beotkkotthon.areyousleeping.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;

    @GetMapping("")
    public ResponseDto<?> getPost(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "keyword", required = false) String keyword) {
        return ResponseDto.ok(postService.getPost(page, size, keyword));
    }
    @PostMapping("")
    public ResponseDto<?> createPost(@RequestBody PostCreateDto postCreateDto) {
        return ResponseDto.created(postService.createPost(postCreateDto));
    }

    @DeleteMapping("/{postId}")
    public ResponseDto<?> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseDto.ok(null);
    }

    @PatchMapping("/{postId}")
    public ResponseDto<?> updatePost(@PathVariable Long postId, @RequestBody PostUpdateDto postUpdateDto) {
        return ResponseDto.ok(postService.updatePost(postId, postUpdateDto));
    }
}
