package com.beotkkotthon.areyousleeping.controller;

import com.beotkkotthon.areyousleeping.annotation.UserId;
import com.beotkkotthon.areyousleeping.dto.global.ResponseDto;
import com.beotkkotthon.areyousleeping.dto.response.FollowingResponseDto;
import com.beotkkotthon.areyousleeping.repository.UserRepository;
import com.beotkkotthon.areyousleeping.service.FollowingService;
import com.beotkkotthon.areyousleeping.domain.User;
import com.beotkkotthon.areyousleeping.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follower")
@Tag(name="Following", description = "유저 간 팔로우 관련 컨트롤러")
public class FollowingController {

    private final FollowingService followingService;
    private final UserRepository userRepository;

    @PostMapping("/{receiverId}")
    @Operation(summary = "토글 형식의 팔로우 & 언팔로우", description = "유저의 Id를 받아 팔로우 되어있지 않다면 팔로우, 팔로우 되어 있으면 언팔로우를 수행합니다.")
    public ResponseDto<?> toggleFollow(@UserId Long senderId, @PathVariable Long receiverId) {

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new EntityNotFoundException("id에 해당되는 유저를 찾을 수 없습니다. " + senderId));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("id에 해당되는 유저를 찾을 수 없습니다. " + receiverId));
        boolean isFollowed = followingService.toggleFollow(sender, receiver);

        String status = isFollowed ? "팔로우" : "언팔로우";
        FollowingResponseDto followingResponseDto = FollowingResponseDto.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .status(status)
                .build();

        return ResponseDto.ok(followingResponseDto);
    }
}
