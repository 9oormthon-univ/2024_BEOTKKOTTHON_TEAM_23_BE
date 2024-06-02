package com.beotkkotthon.areyousleeping.controller;

import com.beotkkotthon.areyousleeping.dto.global.ResponseDto;
import com.beotkkotthon.areyousleeping.dto.request.BoardCreateDto;
import com.beotkkotthon.areyousleeping.dto.request.BoardUpdateDto;
import com.beotkkotthon.areyousleeping.dto.response.BoardResponseDto;
import com.beotkkotthon.areyousleeping.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("")
    public ResponseDto<?> getBoard(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "keyword", required = false) String keyword) {
        return ResponseDto.ok(boardService.getBoard(page, size, keyword));
    }
    @PostMapping("")
    public ResponseDto<?> createBoard(@RequestBody BoardCreateDto boardCreateDto) {
        return ResponseDto.created(boardService.createBoard(boardCreateDto));
    }

    @DeleteMapping("/{boardId}")
    public ResponseDto<?> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseDto.ok(null);
    }

    @PatchMapping("/{boardId}")
    public ResponseDto<?> updateBoard(@PathVariable Long boardId, @RequestBody BoardUpdateDto boardUpdateDto) {
        return ResponseDto.ok(boardService.updateBoard(boardId, boardUpdateDto));
    }
}
