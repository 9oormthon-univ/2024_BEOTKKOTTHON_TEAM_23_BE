package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.domain.Board;
import com.beotkkotthon.areyousleeping.dto.response.BoardDto;
import com.beotkkotthon.areyousleeping.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Board createBoard(BoardDto boardDto) {
        return boardRepository.save(
                Board.builder()
                        .title(boardDto.title())
                        .content(boardDto.content())
                        .user(boardDto.user())
                        .build());
    }
}
