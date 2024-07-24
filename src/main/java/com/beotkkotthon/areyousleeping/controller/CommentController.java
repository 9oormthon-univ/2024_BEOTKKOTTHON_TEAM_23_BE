package com.beotkkotthon.areyousleeping.controller;

import com.beotkkotthon.areyousleeping.annotation.UserId;
import com.beotkkotthon.areyousleeping.dto.global.ResponseDto;
import com.beotkkotthon.areyousleeping.dto.request.CommentCreateDto;
import com.beotkkotthon.areyousleeping.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
@Tag(name="Comment", description = "댓글 CRUD")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("{postId}")
    public ResponseDto<?> getComment(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @PathVariable(value = "postId") Long postId
    ) {
        return ResponseDto.ok(commentService.getComment(page, size, postId));
    }
    @PostMapping("")
    public ResponseDto<?> createComment(@RequestBody CommentCreateDto commentCreateDto, @UserId Long userId) {
        commentService.createComment(commentCreateDto, userId);
        return ResponseDto.created(null);
    }
    @DeleteMapping("/{commentId}")
    public ResponseDto<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseDto.ok(null);
    }
    @PatchMapping("/{commentId}")
    public ResponseDto<?> updateComment(@PathVariable Long commentId, @RequestBody CommentCreateDto commentCreateDto) {
        commentService.updateComment(commentId, commentCreateDto);
        return ResponseDto.ok(null);
    }
}
