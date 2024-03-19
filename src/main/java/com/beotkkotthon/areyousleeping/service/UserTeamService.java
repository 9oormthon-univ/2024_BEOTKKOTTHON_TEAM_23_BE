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

    // 밤샘 참여하기 버튼을 누른 사용자를 팀에 추가하는 메소드
    public UserTeam joinTeam(Long teamId, Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자입니다."));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("해당 팀을 찾을 수 없습니다."));

        // 사용자가 이미 팀에 속해있는지 확인
        boolean isAlreadyJoined = userTeamRepository.existsByUserAndTeam(user, team);
        if (isAlreadyJoined){
            throw new IllegalStateException("유저가 이미 팀에 속해있습니다.");
        }

        // 팀의 현재 인원이 최대 인원을 초과하는지 확인
        if(team.getCurrentNum() >= team.getMaxNum()){
            throw new IllegalStateException("팀의 모집 인원을 초과하여 팀에 참여할 수 없습니다.");
        }

        // UserTeam 객체 생성 및 저장
        UserTeam userTeam = UserTeam.builder()
                .user(user)
                .team(team)
                .isLeader(false)
                .build();
        userTeam = userTeamRepository.save(userTeam);

        // 팀의 현재 인원 수를 1 증가시키고 저장
        team.addMember();
        teamRepository.save(team);

        return userTeam;
    }

    public UserTeam leaveTeam(Long teamId, Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자입니다."));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("해당 팀을 찾을 수 없습니다."));

        //UserTeam에서 유저를 제거
        UserTeam userTeam = userTeamRepository.findByUserIdAndTeamId(userId, teamId);
        if (userTeam == null) {
            throw new IllegalArgumentException("해당 팀에 유저가 속해 있지 않습니다.");
        }
        userTeamRepository.delete(userTeam);

        // team의 현재 인원수 감소
        team.decreaseCurrentNum();
        teamRepository.save(team);

        return userTeam;
    }

    public UserTeam updateUserActiveStatus(Long teamId, Long userId, boolean isActive){

        UserTeam userTeam = userTeamRepository.findByUserIdAndTeamId(userId, teamId);

        if (isActive) {
            userTeam.updateByStart(isActive); // 활동 시작 시 호출
        } else {
            userTeam.updateByEnd(isActive); // 활동 종료 시 호출
        }
        userTeamRepository.save(userTeam);

        return userTeam;
    }
}
