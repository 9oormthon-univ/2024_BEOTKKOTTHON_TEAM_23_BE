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
@Table(name = "mission")
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_team_id", nullable = false)
    private UserTeam userTeam;

    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;

    @Column(name = "result", nullable = true)
    private Boolean result = null;

    @Column(name = "result_image_url", nullable = true)
    private String resultImageUrl = null;

    @Column(name = "result_text", nullable = true)
    private String resultText = null;

    @Builder
    private Mission(UserTeam userTeam) {
        this.userTeam = userTeam;
        this.issuedAt = LocalDateTime.now();
    }

    public void updateFail(Boolean result) {
        this.result = result;
    }
    public void updateSuccessByImage(Boolean result, String resultImageUrl) {
        this.result = result;
        this.resultImageUrl = resultImageUrl;
    }

    public void updateSuccessByText(Boolean result, String resultText) {
        this.result = result;
        this.resultText = resultText;
    }
}
