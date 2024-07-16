package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.domain.Comment;
import com.beotkkotthon.areyousleeping.domain.Post;
import com.beotkkotthon.areyousleeping.domain.User;
import com.beotkkotthon.areyousleeping.domain.specification.PostSpecifications;
import com.beotkkotthon.areyousleeping.dto.request.PostCreateDto;
import com.beotkkotthon.areyousleeping.dto.request.PostUpdateDto;
import com.beotkkotthon.areyousleeping.dto.response.PostDetailResponseDto;
import com.beotkkotthon.areyousleeping.dto.response.PostResponseDto;
import com.beotkkotthon.areyousleeping.exception.CommonException;
import com.beotkkotthon.areyousleeping.exception.ErrorCode;
import com.beotkkotthon.areyousleeping.repository.CommentRepository;
import com.beotkkotthon.areyousleeping.repository.LikeRepository;
import com.beotkkotthon.areyousleeping.repository.PostRepository;
import com.beotkkotthon.areyousleeping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    public void createPost(PostCreateDto postCreateDto) {
        User user = userRepository.findById(postCreateDto.userId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        postRepository.save(
                Post.builder()
                        .postTitle(postCreateDto.title())
                        .postContent(postCreateDto.content())
                        .user(user)
                        .build());
    }

    public Map<String, Object> getPost(Integer page, Integer size, String keyword) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Specification<Post> spec = Specification.where(null);
        if (keyword != null) spec = spec.and(PostSpecifications.hasKeyword(keyword));

        Page<Post> posts = postRepository.findAll(spec, pageable);

        Map<String, Object> result = new HashMap<>();
        result.put("hasNext", posts.hasNext());
        result.put("posts", posts.getContent().stream()

                        .map(post -> {
                            Integer likeCount = likeRepository.countByPost(post);
                            return PostResponseDto.fromEntity(post, likeCount);
                        }
                                .toList());


        return result;
    }
    public PostDetailResponseDto getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_POST));
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        return PostDetailResponseDto.fromEntity(post, comments);
    }

    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_POST));
        postRepository.delete(post);
    }

    public void updatePost(Long postId, PostUpdateDto postUpdateDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_POST));
        post.update(postUpdateDto.title(), postUpdateDto.content());

        postRepository.save(post);
    }
}
