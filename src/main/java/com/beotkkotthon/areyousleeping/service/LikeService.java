package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.domain.Like;
import com.beotkkotthon.areyousleeping.dto.response.PostResponseDto;
import com.beotkkotthon.areyousleeping.exception.CommonException;
import com.beotkkotthon.areyousleeping.exception.ErrorCode;
import com.beotkkotthon.areyousleeping.repository.LikeRepository;
import com.beotkkotthon.areyousleeping.repository.PostRepository;
import com.beotkkotthon.areyousleeping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public String toggleLike(Long userId, Long postId) {
        return likeRepository.findByPostIdAndUserId(postId, userId)
                .map(like -> {
                    likeRepository.delete(like);
                    return "Like removed";
                })
                .orElseGet(() -> {
                    likeRepository.save(Like.builder()
                            .user(userRepository.findById(userId)
                                    .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER)))
                            .post(postRepository.findById(postId)
                                    .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_POST)))
                            .build());
                    return "Like added";
                });
    }
    public List<PostResponseDto> getLikedPost(Long userId) {
         return likeRepository.findAllByUserId(userId).stream()
                 .map(Like::getPost)
                 .map(post -> {
                     Integer likeCount = likeRepository.countByPost(post);
                        return PostResponseDto.fromEntity(post, likeCount);
                 })
                 .toList();
    }
}
