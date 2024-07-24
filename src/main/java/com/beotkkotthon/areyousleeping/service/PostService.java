package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.domain.Comment;
import com.beotkkotthon.areyousleeping.domain.Post;
import com.beotkkotthon.areyousleeping.domain.PostImage;
import com.beotkkotthon.areyousleeping.domain.User;
import com.beotkkotthon.areyousleeping.domain.specification.PostSpecifications;
import com.beotkkotthon.areyousleeping.dto.request.PostCreateDto;
import com.beotkkotthon.areyousleeping.dto.request.PostUpdateDto;
import com.beotkkotthon.areyousleeping.dto.response.PostDetailResponseDto;
import com.beotkkotthon.areyousleeping.dto.response.PostResponseDto;
import com.beotkkotthon.areyousleeping.exception.CommonException;
import com.beotkkotthon.areyousleeping.exception.ErrorCode;
import com.beotkkotthon.areyousleeping.repository.*;
import com.beotkkotthon.areyousleeping.utility.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final ImageUtil imageUtil;

    @Transactional
    public void createPost(PostCreateDto postCreateDto, List<MultipartFile> photos, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        Post post = postRepository.save(
                Post.builder()
                        .postTitle(postCreateDto.title())
                        .postContent(postCreateDto.content())
                        .user(user)
                        .build());
        if(photos != null) {
            if(photos.size() > 3) throw new CommonException(ErrorCode.OVER_MAX_IMAGE);
            photos.forEach(photo -> {
                        postImageRepository.save(
                                PostImage.builder()
                                        .post(post)
                                        .imageUrl(imageUtil.uploadPostImageFile(photo, post.getId()))
                                        .build());
                    });
        }
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
                        })
                        .toList());


        return result;
    }
    public PostDetailResponseDto getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_POST));
        List<String> postImageUrl = postImageRepository.findAllImageUrlByPostId(postId);
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        Integer likeCount = likeRepository.countByPost(post);
        return PostDetailResponseDto.fromEntity(post, comments, likeCount, postImageUrl);
    }
    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_POST));
        List<PostImage> postImages = postImageRepository.findAllByPostId(postId);
        if(postImages != null) {
            postImages.forEach(postImage -> imageUtil.deletePostImageFile(postImage.getImageUrl()));
            postImageRepository.deleteAll(postImages);
        }
        postRepository.delete(post);
    }
    @Transactional
    public void updatePost(Long postId, PostUpdateDto postUpdateDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_POST));
        post.update(postUpdateDto.title(), postUpdateDto.content());

        postRepository.save(post);
    }
    @Transactional
    public void deletePostImage(Long postImageId) {
        PostImage postImage = postImageRepository.findById(postImageId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_POST_IMAGE));
        imageUtil.deletePostImageFile(postImage.getImageUrl());
        postImageRepository.delete(postImage);
    }

    @Transactional
    public void addPostImage(Long postId, List<MultipartFile> photos) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_POST));
        if(postImageRepository.countByPostId(postId) >= 3) throw new CommonException(ErrorCode.OVER_MAX_IMAGE);
        photos.forEach(photo -> {
            postImageRepository.save(
                    PostImage.builder()
                            .post(post)
                            .imageUrl(imageUtil.uploadPostImageFile(photo, postId))
                            .build());
        });
    }
}
