package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.domain.User;
import com.beotkkotthon.areyousleeping.dto.request.UserUpdateDto;
import com.beotkkotthon.areyousleeping.dto.response.UserDetailDto;
import com.beotkkotthon.areyousleeping.exception.CommonException;
import com.beotkkotthon.areyousleeping.exception.ErrorCode;
import com.beotkkotthon.areyousleeping.repository.UserRepository;
import com.beotkkotthon.areyousleeping.utility.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ImageUtil imageUtil;

    public UserDetailDto readUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        return UserDetailDto.fromEntity(user);
    }

    @Transactional
    public void updateUser(Long userId, UserUpdateDto requestDto, MultipartFile imgFile) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        String profileImageName = null;
        if (imgFile != null && !imgFile.isEmpty()) {
            profileImageName = imageUtil.uploadImageFile(imgFile, userId);
            user.updateInfoWithImage(requestDto.nickname(), profileImageName);
        }
        else{
            user.updateInfo(requestDto.nickname());
        }
    }

    public boolean checkDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
