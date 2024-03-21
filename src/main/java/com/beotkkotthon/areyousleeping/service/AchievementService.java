package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.domain.Achievement;
import com.beotkkotthon.areyousleeping.repository.AchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;

    public List<Achievement> getUserAchievements(Long userId) {
        return achievementRepository.findByUserId(userId);
    }
}
