package com.beotkkotthon.areyousleeping.domain;

import com.beotkkotthon.areyousleeping.dto.request.AuthSignUpDto;
import com.beotkkotthon.areyousleeping.dto.type.EProvider;
import com.beotkkotthon.areyousleeping.dto.type.ERole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "users")
public class User {
    /* Default Column */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "serial_id", nullable = false, unique = true)
    private String serialId;

    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private EProvider provider;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private ERole role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /* User Info */
    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname="default_nickname";

    @Column(name = "profile_image_url", nullable = false)
    private String profileImageUrl = "default_profile.png";

    /* User Status */
    @Column(name = "is_login", columnDefinition = "TINYINT(1)")
    private Boolean isLogin;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Builder
    public User(String serialId, String password, EProvider provider, ERole role) {
        this.serialId = serialId;
        this.password = password;
        this.provider = provider;
        this.role = role;
        this.createdAt = LocalDateTime.now();
        this.profileImageUrl = "default_profile.png";
        this.isLogin = false;
    }

    public void register(String nickname) {
        this.nickname = nickname;
        this.role = ERole.USER;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateInfoOnlyNickname(String nickname) {
        if (nickname != null && (!Objects.equals(this.nickname, nickname))) {
            this.nickname = nickname;
        }
    }

    public void updateInfoOnlyImage(String profileImageName) {
        if (profileImageName != null && (!Objects.equals(this.profileImageUrl, profileImageName))) {
            this.profileImageUrl = profileImageName;
        }
    }

    public void updateInfo (String nickname, String profileImageName) {
        if (nickname != null && (!Objects.equals(this.nickname, nickname))) {
            this.nickname = nickname;
        }

        if (profileImageName != null && (!Objects.equals(this.profileImageUrl, profileImageName))) {
            this.profileImageUrl = profileImageName;
        }
    }

    public static User signUp(AuthSignUpDto authSignUpDto, String encodedPassword) {
        return User.builder()
                .serialId(authSignUpDto.serialId())
                .password(encodedPassword)
                .provider(EProvider.DEFAULT)
                .role(ERole.USER)
                .build();
    }
    public static User signUp(String serialId, EProvider provider) {
        return User.builder()
                .serialId(serialId)
                .provider(provider)
                .password(null)
                .role(ERole.USER)
                .build();
    }
}
