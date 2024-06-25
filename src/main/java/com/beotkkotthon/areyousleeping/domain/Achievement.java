package com.beotkkotthon.areyousleeping.domain;

import com.beotkkotthon.areyousleeping.dto.response.AchievementRateDto;
import com.beotkkotthon.areyousleeping.dto.type.EAchievement;
import com.beotkkotthon.areyousleeping.exception.CommonException;
import com.beotkkotthon.areyousleeping.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "achievement")
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "achievement_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "difficulty", nullable = false)
    private Integer difficulty;

    @Column(name = "achievement_image_url", nullable = true)
    private String achievementImageUrl;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Achievement(User user, String title, String content, Integer difficulty, String achievementImageUrl) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.difficulty = difficulty;
        this.achievementImageUrl = achievementImageUrl;
        this.createdAt = LocalDateTime.now();
    }

    public void renewalAchievements(User user, AchievementRateDto achievementRateDto){
        // 잠만보 칭호 수여 로직. 이미 갖고있는 잠만보 칭호가 없고 && 밤샘 참여 횟수가 1회 이상이라면 수여
        if (!(this.title.equals(EAchievement.JAMMANBO.getTitle())) && achievementRateDto.allnightCount() >= 1) {
            this.user = user;
            this.title = EAchievement.JAMMANBO.getTitle();
            this.content = EAchievement.JAMMANBO.getContent();
            this.difficulty = EAchievement.JAMMANBO.getDifficulty();
            this.achievementImageUrl = "https://areyousleeping.s3.ap-northeast-2.amazonaws.com/achievement/achievement_1.png";
        }
        // 올빼미 칭호 수여 로직. 이미 갖고있는 올빼미 칭호가 없고 && 밤샘 참여 10시간 이상
        if (!(this.title.equals(EAchievement.OWL.getTitle())) && TimeUnit.MINUTES.toHours(achievementRateDto.allnightTotal()) >= 10) {
            this.user = user;
            this.title = EAchievement.OWL.getTitle();
            this.content = EAchievement.OWL.getContent();
            this.difficulty = EAchievement.OWL.getDifficulty();
            this.achievementImageUrl = "https://areyousleeping.s3.ap-northeast-2.amazonaws.com/achievement/achievement_2.png";
        }
        // 낮밤이 바뀐자 수여 로직. 이미 갖고있는 낮밤이 바뀐자 칭호가 없고 && 연속 밤샘 4일 이상
        if (!(this.title.equals(EAchievement.DAYANDNIGHTCHANGER.getTitle())) && achievementRateDto.continuousAllnightCount() >= 4) {
            this.user = user;
            this.title = EAchievement.DAYANDNIGHTCHANGER.getTitle();
            this.content = EAchievement.DAYANDNIGHTCHANGER.getContent();
            this.difficulty = EAchievement.DAYANDNIGHTCHANGER.getDifficulty();
            this.achievementImageUrl = "https://areyousleeping.s3.ap-northeast-2.amazonaws.com/achievement/achievement_3.png";
        }
        // 밤의 수호자 수여 로직. 이미 갖고있는 밤의 수호자 칭호가 없고 && 밤샘 총 시간 50시간 이상
        if (!(this.title.equals(EAchievement.GURDIANOFNIGHT.getTitle())) && TimeUnit.MINUTES.toHours(achievementRateDto.allnightTotal()) >= 50) {
            this.user = user;
            this.title = EAchievement.GURDIANOFNIGHT.getTitle();
            this.content = EAchievement.GURDIANOFNIGHT.getContent();
            this.difficulty = EAchievement.GURDIANOFNIGHT.getDifficulty();
            this.achievementImageUrl = "https://areyousleeping.s3.ap-northeast-2.amazonaws.com/achievement/achievement_4.png";
        }
        // 밤샘 마스터 수여 로직. 이미 갖고있는 밤샘 마스터 칭호가 없고 && 최소 6시간 이상 밤샘을 채운 횟수가 10회 이상
        if (!(this.title.equals(EAchievement.ALLNIGHTMASTER.getTitle())) && achievementRateDto.idealAllnightCount() >= 10) {
            this.user = user;
            this.title = EAchievement.ALLNIGHTMASTER.getTitle();
            this.content = EAchievement.ALLNIGHTMASTER.getContent();
            this.difficulty = EAchievement.ALLNIGHTMASTER.getDifficulty();
            this.achievementImageUrl = "https://areyousleeping.s3.ap-northeast-2.amazonaws.com/achievement/achievement_5.png";
        }
        // 고독한 밤샘가 수여 로직. 이미 갖고있는 고독한 밤샘가 칭호가 없고 && 1인 밤샘 5번 이상
        if (!(this.title.equals(EAchievement.LONELYALLNIGHTER.getTitle())) && achievementRateDto.aloneAllnightCount() >= 5) {
            this.user = user;
            this.title = EAchievement.LONELYALLNIGHTER.getTitle();
            this.content = EAchievement.LONELYALLNIGHTER.getContent();
            this.difficulty = EAchievement.LONELYALLNIGHTER.getDifficulty();
            this.achievementImageUrl = "https://areyousleeping.s3.ap-northeast-2.amazonaws.com/achievement/achievement_6.png";
        }
        // 밤을 다스리는 자 수여 로직. 이미 갖고있는 밤을 다스리는 자 칭호가 없고 && 방장으로 참여한 밤샘 총시간 50시간 이상
        if (!(this.title.equals(EAchievement.GOVERNEROFNIGHT.getTitle())) && TimeUnit.MINUTES.toHours(achievementRateDto.leaderAllnightTotal()) >= 50) {
            this.user = user;
            this.title = EAchievement.GOVERNEROFNIGHT.getTitle();
            this.content = EAchievement.GOVERNEROFNIGHT.getContent();
            this.difficulty = EAchievement.GOVERNEROFNIGHT.getDifficulty();
            this.achievementImageUrl = "https://areyousleeping.s3.ap-northeast-2.amazonaws.com/achievement/achievement_7.png";
        }
        // 불면의 달인 수여 로직. 이미 갖고있는 불면의 달인 칭호가 없고 && 밤샘 총시간 100시간
        if (!(this.title.equals(EAchievement.SLEEPLESSMASTER.getTitle())) && TimeUnit.MINUTES.toHours(achievementRateDto.allnightTotal()) >= 100) {
            this.user = user;
            this.title = EAchievement.SLEEPLESSMASTER.getTitle();
            this.content = EAchievement.SLEEPLESSMASTER.getContent();
            this.difficulty = EAchievement.SLEEPLESSMASTER.getDifficulty();
            this.achievementImageUrl = "https://areyousleeping.s3.ap-northeast-2.amazonaws.com/achievement/achievement_8.png";
        }
    }
}
