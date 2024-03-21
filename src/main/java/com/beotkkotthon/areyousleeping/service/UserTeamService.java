package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.domain.*;
import com.beotkkotthon.areyousleeping.dto.response.TeamMemberInfoDto;
import com.beotkkotthon.areyousleeping.exception.CommonException;
import com.beotkkotthon.areyousleeping.exception.ErrorCode;
import com.beotkkotthon.areyousleeping.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTeamService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final UserTeamRepository userTeamRepository;
    private final AchievementRepository achievementRepository;
    private final AllNightersRepository allNightersRepository;

    // 밤샘 참여하기 버튼을 누른 사용자를 팀에 추가하는 메소드
    @Transactional
    public UserTeam joinTeam(Long teamId, Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TEAM));

        // 사용자가 이미 팀에 속해있는지 확인
        boolean isAlreadyJoined = userTeamRepository.existsByUserAndTeam(user, team);
        if (isAlreadyJoined){
            throw new CommonException(ErrorCode.ALREADY_JOINED_TEAM);
        }

        // 팀의 현재 인원이 최대 인원을 초과하는지 확인
        if(team.getCurrentNum() >= team.getMaxNum()){
            throw new CommonException(ErrorCode.OVER_MAX_NUM_OF_TEAM);
        }

        // UserTeam 객체 생성 및 저장
        UserTeam userTeam = UserTeam.builder()
                .user(user)
                .team(team)
                .historyTeamId(team.getId())
                .isLeader(false)
                .build();

        userTeam = userTeamRepository.save(userTeam);

        // 팀의 현재 인원 수를 1 증가시키고 저장
        team.addMember();
        teamRepository.save(team);

        return userTeam;
    }

    // 유저가 팀에서 나가기
    @Transactional
    public UserTeam leaveTeam(Long teamId, Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TEAM));

        UserTeam userTeam = userTeamRepository.findByUserIdAndTeamId(userId, teamId);
        if (userTeam == null) {
            throw new CommonException(ErrorCode.NOT_MATCH_USER_TEAM);
        }

        if(userTeam.getIsActive()){ // 밤샘 중이라면 밤샘 종료 업데이트 로직까지 수행
            userTeam.updateByQuit(); // userTeam의 team을 null로 설정
            userTeamRepository.save(userTeam);
            AllNighters allNighters = allNightersRepository.findByUserTeamId(userTeam.getId());
            allNighters.updateByEnd(); // 밤샘 종료 시간 업데이트(끝 시간 - 시작시간 = 밤샘 시간: Duration)
            allNightersRepository.save(allNighters);
        } else { // 밤샘중이 아니라면 그냥 userTeam의 team을 null로 설정하고 끝
            userTeam.updateByQuit();
            userTeamRepository.save(userTeam);
        }

        // team의 현재 인원수 감소
        team.decreaseCurrentNum();
        // 만약 현재 인원수가 0이라면 팀 삭제
        if (team.getCurrentNum() == 0) {
            teamRepository.delete(team);
        } else {
            teamRepository.save(team);
        }

        return userTeam;
    }

    // 유저의 밤샘 활성화 상태 업데이트
    @Transactional
    public UserTeam updateUserActiveStatus(Long teamId, Long userId, boolean isActive){

        UserTeam userTeam = userTeamRepository.findByUserIdAndTeamId(userId, teamId);
        if (userTeam == null) {
            throw new CommonException(ErrorCode.NOT_MATCH_USER_TEAM);
        }

        // 현재 isActive가 true일 때 lastActiveAt을 현재 시간으로 업데이트
        if (userTeam.getIsActive() != isActive) { // 현재 상태와 요청된 상태가 다를 경우에만 실행
            if (isActive) {
                userTeam.updateByStart(isActive); // 밤샘 시작
                allNightersRepository.save(AllNighters.builder()
                        .userTeam(userTeam)
                        .build());
            } else {
                userTeam.updateByEnd(isActive); // 밤샘 종료
                AllNighters allNighters = allNightersRepository.findByUserTeamId(userTeam.getId());
                allNighters.updateByEnd(); // 밤샘 종료 시간 업데이트(끝 시간 - 시작시간 = 밤샘 시간: Duration)
                allNightersRepository.save(allNighters);
            }
            userTeamRepository.save(userTeam);
        }
        return userTeam;
    }

    // 밤샘 활성화되어 있는 멤버의 수를 조회
    @Transactional(readOnly = true)
    public Long getActiveMembersCount(Long teamId){

        if (!teamRepository.existsById(teamId)){
            throw new CommonException(ErrorCode.NOT_FOUND_TEAM);
        }
        return userTeamRepository.countByTeamIdAndIsActiveTrue(teamId);
    }

    // 밤샘 메이트 조회
    @Transactional(readOnly = true)
    public List<TeamMemberInfoDto> getTeamMembersInfo(Long teamId){

        // 해당 팀에 속한 모든 유저 조회
        List<UserTeam> userTeams = userTeamRepository.findAllByTeamId(teamId);

        if (userTeams.isEmpty()) {
            throw new CommonException(ErrorCode.NOT_MATCH_USER_TEAM);
        }

        List<TeamMemberInfoDto> teamMembersInfo = new ArrayList<>();
        for (UserTeam userTeam : userTeams) {

            User user = userTeam.getUser();

            Achievement latestAchievement = achievementRepository.findTopByUserIdOrderByCreatedAtDesc(user.getId());

            if (latestAchievement == null) {

                // 칭호가 없는 경우, 기본값 설정
                String defaultTitle = "잠만보";
                String defaultContent = "기본값 내용";
                Integer defaultDifficulty = 1;

                Achievement defaultAchievement = Achievement.builder()
                        .user(user)
                        .title(defaultTitle)
                        .content(defaultContent)
                        .difficulty(defaultDifficulty)
                        .build();

                teamMembersInfo.add(TeamMemberInfoDto.builder()
                        .userTeam(userTeam)
                        .user(user)
                        .achievement(defaultAchievement)
                        .build());

            } else {
                teamMembersInfo.add(TeamMemberInfoDto.builder()
                        .userTeam(userTeam)
                        .user(user)
                        .achievement(latestAchievement)
                        .build());
            }
        }
        return teamMembersInfo;
    }

    @Transactional
    public void removeTeamMember(Long requesterId, Long teamId, Long userId) throws IllegalAccessException, IllegalArgumentException{

        UserTeam requesterUserTeam = userTeamRepository.findByUserIdAndTeamId(requesterId, teamId);
        if (requesterUserTeam == null) {
            throw new IllegalArgumentException("요청한 유저는 팀에 속해있지 않습니다.");
        }

        // 요청한 유저가 해당 팀의 방장인지 확인
        boolean isLeader = requesterUserTeam.getIsLeader();
        if (!isLeader){
            throw new IllegalAccessException("방장만 팀원을 추방할 수 있습니다.");
        }

        // 추방하려는 유저가 해당 팀에 속해있는지 확인
        UserTeam userTeam = userTeamRepository.findByUserIdAndTeamId(userId, teamId);
        if (userTeam==null){
            throw new IllegalArgumentException("해당 유저는 팀에 속해있지 않습니다.");
        }

        // 본인 추방 불가
        if (userId.equals(requesterId)){
            throw new IllegalArgumentException("본인을 추방할 수 없습니다.");
        }

        // 팀에서 userId에 해당하는 유저를 제거
        userTeamRepository.delete(userTeam);

        // 방 인원 수 감소
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("해당 팀을 찾을 수 없습니다."));
        team.decreaseCurrentNum();
        teamRepository.save(team);
    }
}
