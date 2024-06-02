package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.domain.Board;
import com.beotkkotthon.areyousleeping.domain.specification.BoardSpecifications;
import com.beotkkotthon.areyousleeping.dto.request.BoardCreateDto;
import com.beotkkotthon.areyousleeping.dto.request.BoardUpdateDto;
import com.beotkkotthon.areyousleeping.dto.response.BoardResponseDto;
import com.beotkkotthon.areyousleeping.exception.CommonException;
import com.beotkkotthon.areyousleeping.exception.ErrorCode;
import com.beotkkotthon.areyousleeping.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Board createBoard(BoardCreateDto boardCreateDto) {
        return boardRepository.save(
                Board.builder()
                        .title(boardCreateDto.title())
                        .content(boardCreateDto.content())
                        .user(boardCreateDto.user())
                        .build());
    }

    public Map<String, Object> getBoard(Integer page, Integer size, String keyword) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Specification<Board> spec = Specification.where(null);
        if (keyword != null) spec = spec.and(BoardSpecifications.hasKeyword(keyword));

        Page<Board> boards = boardRepository.findAll(spec, pageable);

        Map<String, Object> result = new HashMap<>();
        result.put("hasNext", boards.hasNext());
        result.put("boards", boards.getContent().stream()
                .map(BoardResponseDto::fromEntity)
                .collect(Collectors.toList()));

        return result;
    }

    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_BOARD));
        boardRepository.delete(board);
    }

    public Board updateBoard(Long boardId, BoardUpdateDto boardUpdateDto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_BOARD));
        board.update(boardUpdateDto.title(), boardUpdateDto.content());

        return boardRepository.save(board);
    }
}
