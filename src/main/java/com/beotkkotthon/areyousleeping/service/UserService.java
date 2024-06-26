package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.controller.UserTeamController;
import com.beotkkotthon.areyousleeping.domain.Achievement;
import com.beotkkotthon.areyousleeping.domain.User;
import com.beotkkotthon.areyousleeping.domain.UserTeam;
import com.beotkkotthon.areyousleeping.dto.response.UserDetailDto;
import com.beotkkotthon.areyousleeping.exception.CommonException;
import com.beotkkotthon.areyousleeping.exception.ErrorCode;
import com.beotkkotthon.areyousleeping.repository.AchievementRepository;
import com.beotkkotthon.areyousleeping.repository.UserRepository;
import com.beotkkotthon.areyousleeping.repository.UserTeamRepository;
import com.beotkkotthon.areyousleeping.utility.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AchievementRepository achievementRepository;
    private final UserTeamRepository userTeamRepository;
    private final ImageUtil imageUtil;

    public UserDetailDto readUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        Achievement latestAchievement = achievementRepository.findTopByUserIdOrderByCreatedAtDesc(user.getId());

        // latestAchievement가 null인 경우 title을 null로 설정
        String title = (latestAchievement != null) ? latestAchievement.getTitle() : null;

        // 유저 팀 정보 조회
        Optional<UserTeam> userTeamOptional = userTeamRepository.findFirstByUserIdOrderByIdDesc(userId);
        Boolean isInTeam = userTeamOptional.isPresent() && userTeamOptional.get().getTeam() != null;
        Long teamId = isInTeam ? userTeamOptional.get().getTeam().getId() : null;

        return UserDetailDto.fromEntity(user, title, isInTeam, teamId);
    }

    @Transactional
    public void updateUser(Long userId, String nickname, MultipartFile imgFile) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        String profileImageName = null;
        if (imgFile != null && !imgFile.isEmpty()) {
            profileImageName = imageUtil.uploadProfileImageFile(imgFile, userId);
        }

        // 이미지 파일과 닉네임이 모두 제공된 경우
        if (profileImageName != null && nickname != null) {
            user.updateInfo(nickname, profileImageName);
        }
        // 닉네임만 제공된 경우
        else if (nickname != null) {
            user.updateInfoOnlyNickname(nickname);
        }
        // 이미지 파일만 제공된 경우
        else if (profileImageName != null) {
            user.updateInfoOnlyImage(profileImageName);
        }
        // 아무것도 제공되지 않은 경우
        else {
            throw new CommonException(ErrorCode.BAD_REQUEST_JSON);
        }
    }

    public boolean checkDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
