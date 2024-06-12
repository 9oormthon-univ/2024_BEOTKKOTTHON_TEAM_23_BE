package com.beotkkotthon.areyousleeping.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "achievement")
public class AchievementRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "achievement_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "allnight_count")
    private Integer allnightCount;

    @Column(name = "allnight_total")
    private Long allnightTotal;

    @Column(name = "ideal_allnight_count")
    private Integer idealAllnightCount;

    @Column(name = "leader_allnight_total")
    private Long leaderAllnightTotal;

    @Column(name = "alone_allnight_count")
    private Integer aloneAllnightCount;

    @Column(name = "continuous_allnight_count")
    private Integer continuousAllnightCount;

    @Builder
    public AchievementRate(User user, Integer allnightCount, Long allnightTotal, Integer idealAllnightCount, Long leaderAllnightTotal, Integer aloneAllnightCount, Integer continuousAllnightCount){
        this.user = user;
        this.allnightCount = allnightCount;
        this.allnightTotal = allnightTotal;
        this.idealAllnightCount = idealAllnightCount;
        this.leaderAllnightTotal = leaderAllnightTotal;
        this.aloneAllnightCount = aloneAllnightCount;
        this.continuousAllnightCount = continuousAllnightCount;
    }

    public void update(Integer allnightCount, Long allnightTotal, Integer idealAllnightCount, Long leaderAllnightTotal, Integer aloneAllnightCount, Integer continuousAllnightCount){
        this.allnightCount = allnightCount;
        this.allnightTotal = allnightTotal;
        this.idealAllnightCount = idealAllnightCount;
        this.leaderAllnightTotal = leaderAllnightTotal;
        this.aloneAllnightCount = aloneAllnightCount;
        this.continuousAllnightCount = continuousAllnightCount;
    }

    public void updateAchievementRate(UserTeam userTeam, UserTeam lastUserTeam, Integer teamUsersCount) {

        int addedAllnightCount = 0;
        long addedAllnightTotal = 0L;
        int addedIdealAllnightCount = 0;
        long addedLeaderAllnightTotal = 0L;
        int addedAloneAllnightCount = 0;
        int addedContinuousAllnightCount = 0;

        addedAllnightTotal += Duration.between(userTeam.getLastActiveAt(), LocalDateTime.now()).toMinutes();

        if(Duration.between(lastUserTeam.getLastActiveAt(), userTeam.getLastActiveAt()).toHours() > 12) {
            addedAllnightCount = 1;
        }

        if(userTeam.getIsLeader()) {
            addedLeaderAllnightTotal += Duration.between(userTeam.getLastActiveAt(),LocalDateTime.now()).toMinutes();
        }

        if(Duration.between(userTeam.getLastActiveAt(),LocalDateTime.now()).toHours() > 6) {
            addedIdealAllnightCount = 1;
        }

        if(teamUsersCount == 1) {
            addedAloneAllnightCount = 1;
        }

        if(lastUserTeam.getIsActive() && userTeam.getIsActive() && Duration.between(lastUserTeam.getLastActiveAt(), userTeam.getLastActiveAt()).toHours() > 12) {
            addedContinuousAllnightCount = 1;
        }


        this.allnightCount += addedAllnightCount;
        this.allnightTotal += addedAllnightTotal;
        this.idealAllnightCount += addedIdealAllnightCount;
        this.leaderAllnightTotal += addedLeaderAllnightTotal;
        this.aloneAllnightCount += addedAloneAllnightCount;
        this.continuousAllnightCount += addedContinuousAllnightCount;
    }
}
