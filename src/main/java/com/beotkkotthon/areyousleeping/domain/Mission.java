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
@Table(name = "mission")
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_team_id", nullable = false)
    private UserTeam userTeam;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;

    @Column(name = "result", nullable = true)
    private Boolean result = null;

    @Column(name = "result_image_url", nullable = true)
    private String resultImageUrl = null;

    @Column(name = "result_text", nullable = true)
    private String resultText = null;

    @Builder
    private Mission(UserTeam userTeam, String content, LocalDateTime issuedAt, Boolean result, String resultImageUrl, String resultText) {
        this.userTeam = userTeam;
        this.content = content;
        this.issuedAt = issuedAt;
        this.result = result;
        this.resultImageUrl = resultImageUrl;
        this.resultText = resultText;
    }

    public static Mission fromMissionChat(String missionContent, UserTeam userTeam) {
        return Mission.builder()
                .userTeam(userTeam)
                .content(missionContent)
                .issuedAt(LocalDateTime.now())
                .result(null)
                .resultImageUrl(null)
                .resultText(null)
                .build();
    }

    public void updateFail() {
        this.result = false;
    }
    public void updateSuccessByImage(String resultImageUrl) {
        this.result = true;
        this.resultImageUrl = resultImageUrl;
    }

    public void updateSuccessByText(String resultText) {
        this.result = true;
        this.resultText = resultText;
    }
}
