package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.domain.Following;
import com.beotkkotthon.areyousleeping.domain.User;
import com.beotkkotthon.areyousleeping.domain.Achievement;
import com.beotkkotthon.areyousleeping.dto.response.FollowingInfoDto;
import com.beotkkotthon.areyousleeping.exception.CommonException;
import com.beotkkotthon.areyousleeping.exception.ErrorCode;
import com.beotkkotthon.areyousleeping.repository.AchievementRepository;
import com.beotkkotthon.areyousleeping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.beotkkotthon.areyousleeping.repository.FollowingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowingService {

    private final FollowingRepository followingRepository;
    private final UserRepository userRepository;
    private final AchievementRepository achievementRepository;

    @Transactional
    public boolean toggleFollow(User sender, User receiver) {

        // 입력 값 검증
        if (sender == null || receiver == null) {
            throw new IllegalArgumentException("Sender와 receiver는 null값이 될 수 없습니다.");
        }

        // 자기 자신을 팔로우하는 경우
        if (sender.equals(receiver)) {
            throw new IllegalArgumentException("자기 자신을 팔로우할 수 없습니다.");
        }

        Optional<Following> following = followingRepository.findBySenderAndReceiver(sender, receiver);

        if (following.isPresent()) {
            followingRepository.delete(following.get());
            return false; // 언팔로우
        } else {
            Following newFollowing = Following.builder()
                    .sender(sender)
                    .receiver(receiver)
                    .createdAt(LocalDateTime.now())
                    .build();
            followingRepository.save(newFollowing);
            return true; // 팔로우
        }
    }


    @Transactional(readOnly = true)
    public List<FollowingInfoDto> getFollowingInfo(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        // 해당 사용자가 팔로우하는 사람들 찾기
        List<Following> followings = followingRepository.findBySenderId(userId);

        // 팔로우하는 사람들의 nickname과 칭호 가져오기
        List<FollowingInfoDto> followingInfo = followings.stream().map(following -> {
            User followingUser = following.getReceiver();
            Long followingUserId = followingUser.getId();
            Achievement latestAchievement = achievementRepository.findTopByUserIdOrderByCreatedAtDesc(followingUserId);
            String latestAchievementTitle = (latestAchievement != null) ? latestAchievement.getTitle() : "잠만보"; // 칭호가 없는 경우를 대비한 null 체크
            return new FollowingInfoDto(followingUser.getId(), followingUser.getNickname(), latestAchievementTitle);
        }).collect(Collectors.toList());

        return followingInfo;
    }
}
