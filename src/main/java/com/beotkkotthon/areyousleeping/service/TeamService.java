package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.domain.Team;
import com.beotkkotthon.areyousleeping.domain.User;
import com.beotkkotthon.areyousleeping.domain.UserTeam;
import com.beotkkotthon.areyousleeping.domain.nosql.ChatMessageList;

import com.beotkkotthon.areyousleeping.domain.specification.TeamSpecifications;
import com.beotkkotthon.areyousleeping.dto.request.TeamSaveDto;
import com.beotkkotthon.areyousleeping.dto.response.TeamResponseDto;
import com.beotkkotthon.areyousleeping.exception.CommonException;
import com.beotkkotthon.areyousleeping.exception.ErrorCode;
import com.beotkkotthon.areyousleeping.repository.ChatMessageListRepository;
import com.beotkkotthon.areyousleeping.repository.TeamRepository;
import com.beotkkotthon.areyousleeping.repository.UserRepository;
import com.beotkkotthon.areyousleeping.repository.UserTeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserTeamRepository userTeamRepository;
    private final UserRepository userRepository;
    private final ChatMessageListRepository chatMessageListRepository;

    @Transactional
    public TeamResponseDto createTeam(Long userId, TeamSaveDto teamSaveDto) {
        boolean userIsInExistingTeam = userTeamRepository.findAllByUserId(userId).stream()
                .map(UserTeam::getTeam)
                .anyMatch(team -> teamRepository.existsById(team.getId())); // 유저의 userTeam과 연결된 Team 객체가 teamRepository에 존재하는지 확인
                                                                            // 존재하지 않는다면 단순 기록을 위한 userTeam인 것을 의미(죽은 팀)
        if (userIsInExistingTeam) {
            throw new CommonException(ErrorCode.ALREADY_JOINED_TEAM);
        }
        // Team 엔티티 생성 -> 저장
        Team team = teamSaveDto.toEntity();
        team.addMember(); // 팀 생성 시 현재 인원수를 1로 설정
        team = teamRepository.save(team);

        // User 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자입니다."));

        // UserTeam 인스턴스 생성 및 연관관계 설정, 방 생성 시 방장으로 설정
        UserTeam userTeam = UserTeam.builder()
                .user(user)
                .team(team)
                .historyTeamId(team.getId())
                .isLeader(true)
                .build();
        userTeamRepository.save(userTeam);

        ChatMessageList chatMessageList = ChatMessageList.builder()
                        .id(team.getId().toString())
                                .build();
        chatMessageListRepository.save(chatMessageList);

        return TeamResponseDto.fromEntity(team);
    }
    public Map<String, Object> getTeams(Integer page, Integer size, String keyword, String category, Boolean isEmpty, Boolean isPublic) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Specification<Team> spec = Specification.where(null);
        if (keyword != null) spec = spec.and(TeamSpecifications.hasKeyword(keyword));
        if (category != null) spec = spec.and(TeamSpecifications.hasCategory(category));
        if (isEmpty != null) spec = spec.and(TeamSpecifications.isEmpty(isEmpty));
        if (isPublic != null) spec = spec.and(TeamSpecifications.isPublic(isPublic));

        Page<Team> teams = teamRepository.findAll(spec, pageable);

        Map<String, Object> result = new HashMap<>();
        result.put("hasNext", teams.hasNext());
        result.put("teams", teams.getContent().stream()
                .map(TeamResponseDto::fromEntity)
                .collect(Collectors.toList()));

        return result;
    }



}
