package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.domain.Team;
import com.beotkkotthon.areyousleeping.domain.User;
import com.beotkkotthon.areyousleeping.domain.UserTeam;
import com.beotkkotthon.areyousleeping.dto.request.TeamSaveDto;
import com.beotkkotthon.areyousleeping.dto.response.TeamResponseDto;
import com.beotkkotthon.areyousleeping.repository.TeamRepository;
import com.beotkkotthon.areyousleeping.repository.UserRepository;
import com.beotkkotthon.areyousleeping.repository.UserTeamRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final UserTeamRepository userTeamRepository;

    @Transactional
    public Team createTeam(Long userId, TeamSaveDto teamSaveDto){

        // Team 엔티티 생성 -> 저장
        Team team = teamSaveDto.toEntity();
        teamRepository.save(team);

        //User 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자입니다."));

        // UserTeam 인스턴스 생성 및 연관관계 설정
        UserTeam userTeam = UserTeam.builder()
                .user(user)
                .team(team)
                .build();
        userTeamRepository.save(userTeam);

        return team;
    }

}
