package com.beotkkotthon.areyousleeping.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_team")
public class UserTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_team_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = true)
    private Team team;

    @Column(name = "history_team_id", nullable = false)
    private Long historyTeamId;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @Column(name = "is_leader", nullable = false)
    private Boolean isLeader = false;

    @Column(name = "last_active_at", nullable = true)
    private LocalDateTime lastActiveAt = null;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public UserTeam(User user, Team team, Long historyTeamId, Boolean isLeader) {
        this.user = user;
        this.team = team;
        this.historyTeamId = historyTeamId;
        this.isActive = false;
        this.isLeader = isLeader;
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

    public void updateByQuit() {
        this.isActive = false;
        this.team = null;
    }

    public void changeLeader(boolean isLeader){
        this.isLeader = isLeader;
    }

}
