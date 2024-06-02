package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.domain.Comment;
import com.beotkkotthon.areyousleeping.dto.response.CommentResponseDto;
import com.beotkkotthon.areyousleeping.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    public List<CommentResponseDto> getComment(Integer page, Integer size, Long postId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<Comment> comments = commentRepository.findAllByPostId(postId, pageable);

        return comments.getContent().stream()
                .map(CommentResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}
