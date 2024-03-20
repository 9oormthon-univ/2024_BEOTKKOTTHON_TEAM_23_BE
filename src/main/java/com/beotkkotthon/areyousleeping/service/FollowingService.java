package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.domain.Following;
import com.beotkkotthon.areyousleeping.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.beotkkotthon.areyousleeping.repository.FollowingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FollowingService {

    private final FollowingRepository followingRepository;

    @Transactional
    public boolean toggleFollow(User sender, User receiver) {

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
}
