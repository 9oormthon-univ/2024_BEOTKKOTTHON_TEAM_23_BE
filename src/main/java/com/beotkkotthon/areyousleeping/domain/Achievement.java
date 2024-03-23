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
}
