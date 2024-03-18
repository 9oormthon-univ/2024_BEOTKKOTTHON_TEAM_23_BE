package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.domain.Team;
import com.beotkkotthon.areyousleeping.domain.User;
import com.beotkkotthon.areyousleeping.domain.UserTeam;
import com.beotkkotthon.areyousleeping.repository.TeamRepository;
import com.beotkkotthon.areyousleeping.repository.UserRepository;
import com.beotkkotthon.areyousleeping.repository.UserTeamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTeamService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final UserTeamRepository userTeamRepository;

    public void joinTeam(Long teamId, Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자입니다."));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Team not found"));

        // 사용자가 이미 팀에 속해있는지 확인
        boolean isAlreadyJoined = userTeamRepository.existsByUserAndTeam(user, team);
        if (isAlreadyJoined){
            throw new IllegalStateException("유저가 이미 팀에 속해있습니다.");
        }

        // UserTeam 객체 생성 및 저장
        UserTeam userTeam = UserTeam.builder()
                .user(user)
                .team(team)
                .isActive(false) // 팀에 가입하면 활성 상태로 설정
                .isLeader(false) // 기본적으로는 리더가 아님
                .build();
    }
}
