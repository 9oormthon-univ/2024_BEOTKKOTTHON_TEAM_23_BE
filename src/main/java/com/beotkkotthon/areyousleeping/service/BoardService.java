package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.domain.Board;
import com.beotkkotthon.areyousleeping.domain.specification.BoardSpecifications;
import com.beotkkotthon.areyousleeping.dto.response.BoardResponseDto;
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

    public Board createBoard(BoardResponseDto boardResponseDto) {
        return boardRepository.save(
                Board.builder()
                        .title(boardResponseDto.title())
                        .content(boardResponseDto.content())
                        .user(boardResponseDto.user())
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
        boardRepository.deleteById(boardId);
    }
}
