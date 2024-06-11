package com.beotkkotthon.areyousleeping.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
