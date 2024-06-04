package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.domain.Comment;
import com.beotkkotthon.areyousleeping.domain.Post;
import com.beotkkotthon.areyousleeping.domain.User;
import com.beotkkotthon.areyousleeping.dto.request.CommentCreateDto;
import com.beotkkotthon.areyousleeping.dto.response.CommentResponseDto;
import com.beotkkotthon.areyousleeping.exception.CommonException;
import com.beotkkotthon.areyousleeping.exception.ErrorCode;
import com.beotkkotthon.areyousleeping.repository.CommentRepository;
import com.beotkkotthon.areyousleeping.repository.PostRepository;
import com.beotkkotthon.areyousleeping.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    public List<CommentResponseDto> getComment(Integer page, Integer size, Long postId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<Comment> comments = commentRepository.findAllByPostId(postId, pageable);

        return comments.getContent().stream()
                .map(CommentResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Comment createComment(CommentCreateDto commentCreateDto) {
        User user = userRepository.findById(commentCreateDto.userId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        Post post = postRepository.findById(commentCreateDto.postId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_POST));
        return commentRepository.save(
                Comment.builder()
                        .user(user)
                        .post(post)
                        .commentContent(commentCreateDto.commentContent())
                        .build());
    }
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_COMMENT));
        commentRepository.delete(comment);
    }
    public Comment updateComment(Long commentId, CommentCreateDto commentCreateDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_COMMENT));
        comment.update(
                commentCreateDto.commentContent()
        );
        return commentRepository.save(comment);
    }
}
