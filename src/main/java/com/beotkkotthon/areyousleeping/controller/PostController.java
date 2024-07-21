package com.beotkkotthon.areyousleeping.controller;

import com.beotkkotthon.areyousleeping.dto.global.ResponseDto;
import com.beotkkotthon.areyousleeping.dto.request.PostCreateDto;
import com.beotkkotthon.areyousleeping.dto.request.PostUpdateDto;
import com.beotkkotthon.areyousleeping.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
@Tag(name="Post", description = "게시글 CRUD")
public class PostController {
    private final PostService postService;

    @GetMapping("")
    public ResponseDto<?> getPost(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "keyword", required = false) String keyword) {
        return ResponseDto.ok(postService.getPost(page, size, keyword));
    }
    @GetMapping("/{postId}")
    public ResponseDto<?> getPostDetail(@PathVariable Long postId) {
        return ResponseDto.ok(postService.getPostDetail(postId));
    }
    @PostMapping("")
    public ResponseDto<?> createPost(@RequestBody PostCreateDto postCreateDto) {
        postService.createPost(postCreateDto);
        return ResponseDto.created(null);
    }

    @DeleteMapping("/{postId}")
    public ResponseDto<?> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseDto.ok(null);
    }

    @PatchMapping("/{postId}")
    public ResponseDto<?> updatePost(@PathVariable Long postId, @RequestBody PostUpdateDto postUpdateDto) {
        postService.updatePost(postId, postUpdateDto);
        return ResponseDto.ok(null);
    }
}
