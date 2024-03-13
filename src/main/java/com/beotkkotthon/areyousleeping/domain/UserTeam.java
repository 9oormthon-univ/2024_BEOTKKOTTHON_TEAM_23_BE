package com.beotkkotthon.areyousleeping.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_team")
public class UserTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_team_id")
    private Long userTeamId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @Column(name = "last_active_at", nullable = true)
    private LocalDateTime lastActiveAt = null;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    private UserTeam(User user, Team team) {
        this.user = user;
        this.team = team;
        this.isActive = false;
        this.lastActiveAt = null;
        this.createdAt = LocalDateTime.now();
    }

    public void updateByStart(Boolean isActive) {
        this.isActive = isActive;
        this.lastActiveAt = LocalDateTime.now(); // 현재 경과한 시간 표현할 때 사용
    }
    public void updateByEnd(Boolean isActive) {
        this.isActive = isActive;
    }

}
