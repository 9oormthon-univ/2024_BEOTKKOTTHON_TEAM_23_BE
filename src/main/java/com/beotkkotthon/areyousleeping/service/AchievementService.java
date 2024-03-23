package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.domain.Achievement;
import com.beotkkotthon.areyousleeping.domain.AllNighters;
import com.beotkkotthon.areyousleeping.domain.User;
import com.beotkkotthon.areyousleeping.dto.response.AchievementDto;
import com.beotkkotthon.areyousleeping.exception.CommonException;
import com.beotkkotthon.areyousleeping.exception.ErrorCode;
import com.beotkkotthon.areyousleeping.repository.AchievementRepository;
import com.beotkkotthon.areyousleeping.repository.AllNightersRepository;
import com.beotkkotthon.areyousleeping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final UserRepository userRepository;
    private final AllNightersRepository allNightersRepository;

    public List<Achievement> getUserAchievements(Long userId) {
        return achievementRepository.findByUserId(userId);
    }

    public List<AchievementDto> renewalAchievement(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        List<String> lastAchievements = achievementRepository.findByUserId(userId).stream()
                .map(Achievement::getTitle)
                .toList();
        List<AllNighters> allNightersList = allNightersRepository.findByUserId(userId);
        List<AchievementDto> newAchievementsDto = new ArrayList<>();

        int totalDuration = allNightersList.stream()
                .map(AllNighters::getDuration)
                .reduce(0, Integer::sum);
        int totalAllNighters = allNightersList.size();

        if(!lastAchievements.contains("잠만보") && totalAllNighters >= 1){
            Achievement jammanbo = achievementRepository.save(Achievement.builder()
                    .user(user)
                    .title("잠만보")
                    .content("1회 이상 새벽을 새웠습니다.")
                    .difficulty(1)
                    .achievementImageUrl("https://areyousleeping.s3.ap-northeast-2.amazonaws.com/achievement/achievement_1.png")
                    .build());
            newAchievementsDto.add(AchievementDto.fromEntity(jammanbo));
        }
        if(!lastAchievements.contains("올빼미") && totalDuration >= 10){
            Achievement wol = achievementRepository.save(Achievement.builder()
                    .user(user)
                    .title("올빼미")
                    .content("총 10시간 이상 새벽을 새웠습니다.")
                    .difficulty(2)
                    .achievementImageUrl("https://areyousleeping.s3.ap-northeast-2.amazonaws.com/achievement/achievement_2.png")
                    .build());
            newAchievementsDto.add(AchievementDto.fromEntity(wol));
        }
        return newAchievementsDto;
    }
}
