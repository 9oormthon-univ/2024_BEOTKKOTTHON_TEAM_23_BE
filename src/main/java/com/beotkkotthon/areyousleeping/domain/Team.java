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
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "max_num", nullable = false)
    private Integer maxNum;

    @Column(name = "target_time", nullable = false)
    private Integer targetTime;

    @Column(name = "is_secret", nullable = false)
    private Boolean isSecret = false;

    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    private Team(String title, Integer maxNum, Integer targetTime, Boolean isSecret, String password, String category, String description) {
        this.title = title;
        this.maxNum = maxNum;
        this.targetTime = targetTime;
        this.isSecret = isSecret;
        this.password = password;
        this.category = category;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }

    public void update(String title, Integer maxNum, Integer targetTime, Boolean isSecret, String password, String category, String description) {
        this.title = title;
        this.maxNum = maxNum;
        this.targetTime = targetTime;
        this.isSecret = isSecret;
        this.password = password;
        this.category = category;
        this.description = description;
    }
}
