package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.domain.Team;
import com.beotkkotthon.areyousleeping.domain.UserTeam;
import com.beotkkotthon.areyousleeping.domain.nosql.ChatMessageList;
import com.beotkkotthon.areyousleeping.dto.request.TeamCreateRequestDto;
import com.beotkkotthon.areyousleeping.repository.ChatMessageListRepository;
import com.beotkkotthon.areyousleeping.repository.TeamRepository;
import com.beotkkotthon.areyousleeping.repository.UserRepository;
import com.beotkkotthon.areyousleeping.repository.UserTeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserTeamRepository userTeamRepository;
    private final UserRepository userRepository;
    private final ChatMessageListRepository chatMessageListRepository;

    public Team createTeam(Long userId, TeamCreateRequestDto requestDto) {
        Team team = Team.builder()
                .title(requestDto.title())
                .maxNum(requestDto.maxNum())
                .targetTime(requestDto.targetTime())
                .isSecret(requestDto.isSecret())
                .password(requestDto.password())
                .category(requestDto.category())
                .description(requestDto.description())
                .build();
        teamRepository.save(team);

        UserTeam userTeam = UserTeam.builder()
                .user(userRepository.findById(userId).get())
                .team(team)
                .build();
        userTeamRepository.save(userTeam);

        ChatMessageList chatMessageList = ChatMessageList.builder()
                        .id(team.getId().toString())
                                .build();
        chatMessageListRepository.save(chatMessageList);
        return team;
    }
}
