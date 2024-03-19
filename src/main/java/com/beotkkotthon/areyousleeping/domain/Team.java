package com.beotkkotthon.areyousleeping.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
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

    @Column(name = "current_num", nullable = false)
    private Integer currentNum=0;

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

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    private Team(String title, Integer maxNum, Integer currentNum, Integer targetTime, Boolean isSecret, String password, String category, String description) {
        this.title = title;
        this.maxNum = maxNum;
        this.currentNum = currentNum;
        this.targetTime = targetTime;
        this.isSecret = isSecret;
        this.password = password;
        this.category = category;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }

    public void update(String title, Integer maxNum, Integer currentNum, Integer targetTime, Boolean isSecret, String password, String category, String description) {
        this.title = title;
        this.maxNum = maxNum;
        this.currentNum = currentNum;
        this.targetTime = targetTime;
        this.isSecret = isSecret;
        this.password = password;
        this.category = category;
        this.description = description;
    }

    // 멤버 추가 시 currentNum 증가 메서드
    public void addMember() {
        if(this.currentNum==null){
            this.currentNum=0;
        }
        this.currentNum += 1;
    }

    // 멤버가 팀 나갈 때 currentNum 감소 메서드
    public void decreaseCurrentNum() {
        if (this.currentNum > 0) {
            this.currentNum -= 1; // 현재 인원이 0보다 클 때만 감소
        } else{
            throw new IllegalStateException("팀 인원이 이미 0명입니다.");
        }
    }
}
