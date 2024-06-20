package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.domain.*;
import com.beotkkotthon.areyousleeping.dto.response.AchievementRateDto;
import com.beotkkotthon.areyousleeping.dto.response.TeamMemberInfoDto;
import com.beotkkotthon.areyousleeping.dto.type.EAchievement;
import com.beotkkotthon.areyousleeping.exception.CommonException;
import com.beotkkotthon.areyousleeping.exception.ErrorCode;
import com.beotkkotthon.areyousleeping.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserTeamService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final UserTeamRepository userTeamRepository;
    private final AchievementRepository achievementRepository;
    private final AchievementRateRepository achievementRateRepository;
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

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TEAM));

        UserTeam userTeam = userTeamRepository.findByUserIdAndTeamId(userId, teamId);
        if (userTeam == null) {
            throw new CommonException(ErrorCode.NOT_MATCH_USER_TEAM);
        }

        // 유저가 밤샘 참여중인지 확인
        if (userTeam.getIsActive()) {
            // 밤샘 참여중이라면, 밤샘 종료 후 팀 나가기
            UserTeam lastUserTeam = userTeamRepository.findAllByUserIdOrderByCreatedAtDesc(userId).get(1);
            achievementRateRepository.findByUserId(userId).updateAchievementRate(userTeam, lastUserTeam, team.getCurrentNum());

            User user = userRepository.findById(userId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
            AchievementRateDto achievementRateDto = AchievementRateDto.fromEntity(achievementRateRepository.findByUserId(userId));
            achievementRepository.findByUserId(userId).forEach(achievement ->
                    achievement.renewalAchievements(user, achievementRateDto)
            );
            userTeam.updateByQuit();
        }

        Boolean isLeader = userTeam.getIsLeader(); // 팀을 떠나는 사용자가 리더인지 확인

        if (isLeader){
            // 팀 나가기를 요청한 유저가 방장인지 확인
            // 방장이 나갔을 때 user_team_id가 가장 작은 멤버를 새로운 방장으로 설정
            List<UserTeam> users = userTeamRepository.findByTeamIdOrderByIdAsc(teamId);
            userTeam.changeLeader(false); // 기존 리더의 isLeader 상태를 false로 설정

            // user_team_id가 가장 작은 사용자를 새로운 리더로 설정 (기존 리더 제외)
            UserTeam newLeader = users.stream()
                    .filter(u -> !u.getUser().getId().equals(userId))
                    .min(Comparator.comparing(UserTeam::getId))
                    .orElse(null);

            if (newLeader != null) {
                newLeader.changeLeader(true);
                userTeamRepository.save(newLeader);
            }
        }

        // 변경된 리더 상태 저장 및 팀 인원 수 감소와 팀 삭제 로직을 포함하는 메소드 호출
        removeUserTeamAndUpdateTeamNum(userTeam, team, false, userId); // 더 이상 여기에서 userTeam에 대한 변경을 저장하지 않음

        // userTeam의 team을 null로 설정하고 저장하는 로직은 removeUserTeamAndUpdateTeamNum 메소드 내로 이동되었습니다.
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
                UserTeam lastUserTeam = userTeamRepository.findAllByUserIdOrderByCreatedAtDesc(userId).get(1);
                Integer teamUsersCount = teamRepository.findById(teamId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TEAM)).getCurrentNum();

                achievementRateRepository.findByUserId(userId).updateAchievementRate(userTeam, lastUserTeam, teamUsersCount);

                User user = userRepository.findById(userId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
                achievementRepository.findByUserId(userId).forEach(achievement ->
                        achievement.renewalAchievements(user,
                                AchievementRateDto.fromEntity(
                                        achievementRateRepository.findByUserId(userId)
                                )
                        )
                );
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

            // latestAchievement가 null인 경우를 처리. 업적이 없는 경우 title을 null로 설정
            String title = (latestAchievement != null) ? latestAchievement.getTitle() : null;

            TeamMemberInfoDto teamMemberInfoDto = TeamMemberInfoDto.builder()
                    .isActive(userTeam.getIsActive())
                    .isLeader(userTeam.getIsLeader())
                    .nickname(user.getNickname())
                    .profileImgUrl(user.getProfileImageUrl())
                    .title(title)
                    .build();
            teamMembersInfo.add(teamMemberInfoDto);
        }

        return teamMembersInfo;
    }

    @Transactional
    public void expelTeamMember(Long requesterId, Long teamId, Long userId) {

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TEAM));

        UserTeam requesterUserTeam = userTeamRepository.findByUserIdAndTeamId(requesterId, teamId);
        if (requesterUserTeam == null) {
            throw new CommonException(ErrorCode.NOT_MATCH_USER_TEAM);
        }

        // 요청한 유저가 해당 팀의 방장인지 확인
        boolean isLeader = requesterUserTeam.getIsLeader();
        if (!isLeader){
            throw new CommonException(ErrorCode.NOT_MATCH_LEADER);
        }

        // 추방하려는 유저가 해당 팀에 속해있는지 확인
        UserTeam userTeam = userTeamRepository.findByUserIdAndTeamId(userId, teamId);
        if (userTeam==null){
            throw new CommonException(ErrorCode.NOT_MATCH_USER_TEAM);
        }

        // 본인 추방 불가
        if (userId.equals(requesterId)){
            throw new CommonException(ErrorCode.BAD_REQUEST_PARAMETER);
        }

        removeUserTeamAndUpdateTeamNum(userTeam, team, true, userId);
    }


    @Transactional
    public void removeUserTeamAndUpdateTeamNum(UserTeam userTeam, Team team, boolean isSaveUserTeam, Long userId){
        // 유저가 밤샘 참여중인지 확인
        if (userTeam.getIsActive()) {
            // 밤샘 참여중이라면, 밤샘 종료 후 팀 나가기
            UserTeam lastUserTeam = userTeamRepository.findAllByUserIdOrderByCreatedAtDesc(userId).get(1);
            achievementRateRepository.findByUserId(userId).updateAchievementRate(userTeam, lastUserTeam, team.getCurrentNum());

            User user = userRepository.findById(userId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
            AchievementRateDto achievementRateDto = AchievementRateDto.fromEntity(achievementRateRepository.findByUserId(userId));
            achievementRepository.findByUserId(userId).forEach(achievement ->
                    achievement.renewalAchievements(user, achievementRateDto)
            );

            userTeam.updateByQuit();

            if (userTeam.getIsLeader()) { // 사용자가 리더인 경우 isLeader 상태 업데이트
                userTeam.changeLeader(false); // 방장 상태를 false로 변경
            }

            AllNighters allNighters = allNightersRepository.findByUserTeamId(userTeam.getId());
            allNighters.updateByEnd(); // 밤샘 종료 시간 업데이트
            allNightersRepository.save(allNighters);

        } else { // 밤샘중이 아니라면 그냥 userTeam의 team을 null로 설정하고 끝
            userTeam.updateByQuit();

            if (userTeam.getIsLeader()) { // 사용자가 리더인 경우 isLeader 상태 업데이트
                userTeam.changeLeader(false); // 방장 상태를 false로 변경
            }
        }

        if (isSaveUserTeam) {
            userTeamRepository.save(userTeam); // 여기에서 userTeam의 최종 상태를 저장
        }

        // team의 인원 수 감소
        team.decreaseCurrentNum();

        // 만약 현재 인원수가 0이라면 팀 삭제
        if (team.getCurrentNum() == 0) {
            teamRepository.delete(team);
        } else {
            teamRepository.save(team);
        }

    }
}
