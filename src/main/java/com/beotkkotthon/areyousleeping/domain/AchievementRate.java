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
    private Long allnightCount;

    @Column(name = "allnight_total")
    private Long allnightTotal;

    @Column(name = "ideal_allnight_count")
    private Long idealAllnightCount;

    @Column(name = "leader_allnight_total")
    private Long leaderAllnightTotal;

    @Column(name = "alone_allnight_count")
    private Long aloneAllnightCount;

    @Builder
    public AchievementRate(User user, Long allnightCount, Long allnightTotal, Long idealAllnightCount, Long leaderAllnightTotal, Long aloneAllnightCount){
        this.user = user;
        this.allnightCount = allnightCount;
        this.allnightTotal = allnightTotal;
        this.idealAllnightCount = idealAllnightCount;
        this.leaderAllnightTotal = leaderAllnightTotal;
        this.aloneAllnightCount = aloneAllnightCount;
    }
}
