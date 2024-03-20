package com.beotkkotthon.areyousleeping.controller;

import com.beotkkotthon.areyousleeping.annotation.UserId;
import com.beotkkotthon.areyousleeping.dto.global.ResponseDto;
import com.beotkkotthon.areyousleeping.repository.UserRepository;
import com.beotkkotthon.areyousleeping.service.FollowingService;
import com.beotkkotthon.areyousleeping.domain.User;
import com.beotkkotthon.areyousleeping.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follow")
@Tag(name="Following", description = "유저 간 팔로우 관련 컨트롤러")
public class FollowingController {

    private final FollowingService followingService;
    private final UserRepository userRepository;

    @PostMapping("/{senderId}/{receiverId}")
    public ResponseDto<String> toggleFollow(@UserId Long senderId, @UserId Long receiverId) {

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new EntityNotFoundException("id에 해당되는 유저를 찾을 수 없습니다. " + senderId));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("id에 해당되는 유저를 찾을 수 없습니다. " + receiverId));
        boolean isFollowed = followingService.toggleFollow(sender, receiver);

        return ResponseDto.ok(isFollowed ? "팔로우" : "언팔로우");
    }
}
