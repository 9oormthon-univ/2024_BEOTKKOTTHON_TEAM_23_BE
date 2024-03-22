package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.constants.Constants;
import com.beotkkotthon.areyousleeping.domain.Mission;
import com.beotkkotthon.areyousleeping.domain.Team;
import com.beotkkotthon.areyousleeping.domain.User;
import com.beotkkotthon.areyousleeping.domain.UserTeam;
import com.beotkkotthon.areyousleeping.domain.nosql.ChatMessage;
import com.beotkkotthon.areyousleeping.dto.request.MissionImageDto;
import com.beotkkotthon.areyousleeping.dto.request.MissionTextDto;
import com.beotkkotthon.areyousleeping.dto.response.ChatMessageResponseDto;
import com.beotkkotthon.areyousleeping.dto.response.MissionDto;
import com.beotkkotthon.areyousleeping.exception.CommonException;
import com.beotkkotthon.areyousleeping.exception.ErrorCode;
import com.beotkkotthon.areyousleeping.repository.MissionRepository;
import com.beotkkotthon.areyousleeping.repository.UserTeamRepository;
import com.beotkkotthon.areyousleeping.utility.ImageUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final SimpMessagingTemplate messagingTemplate;
    private final MissionRepository missionRepository;
    private final UserTeamRepository userTeamRepository;
    private ScheduledExecutorService scheduler;
    private final ImageUtil imageUtil;

    private final Random random = new Random();

    private List<String> availableMissions;
    private List<String> usedMissions = new ArrayList<>();
    @Transactional
    public void updateMissionResultPic(Long userId, Long teamId, MissionImageDto requestDto, MultipartFile imageFile) {
        if (requestDto.result()) {
            String resultImageUrl = imageUtil.uploadMissionImageFile(imageFile, userId, teamId);
            Optional<Mission> missionOptional = missionRepository.findFirstByUserTeamIdOrderByIssuedAtDesc(
                    userTeamRepository.findByUserIdAndTeamId(userId, teamId).getId()
            );
            missionOptional.ifPresent(mission -> {
                mission.updateSuccessByImage(resultImageUrl);
            });
        } else {
            Optional<Mission> missionOptional = missionRepository.findFirstByUserTeamIdOrderByIssuedAtDesc(
                    userTeamRepository.findByUserIdAndTeamId(userId, teamId).getId()
            );
            missionOptional.ifPresent(Mission::updateFail);
        }
    }

    @Transactional
    public void updateMissionResultText(Long userId, Long teamId, MissionTextDto requestDto) {
        Optional<Mission> missionOptional = missionRepository.findFirstByUserTeamIdOrderByIssuedAtDesc(
                userTeamRepository.findByUserIdAndTeamId(userId, teamId).getId()
        );
        if (requestDto.result()) {
            missionOptional.ifPresent(mission -> {
                mission.updateSuccessByText(requestDto.resultText());
            });
        } else {
            missionOptional.ifPresent(Mission::updateFail);
        }
    }

    public List<MissionDto> getMissionTimeline(Long teamId) {
        // userTeam의 historyTeamId가 teamId와 같은 userTeam들을 조회
        List<UserTeam> userTeams = userTeamRepository.findAllByHistoryTeamId(teamId);
        if (userTeams.isEmpty()) {
            throw new CommonException(ErrorCode.NOT_FOUND_TEAM);
        }

        return userTeams.stream()
                .map(userTeam -> missionRepository.findAllByUserTeamIdOrderByIssuedAtAsc(userTeam.getId())
                        .stream()
                        .map(MissionDto::fromEntity)
                        .toList())
                .flatMap(Collection::stream)
                .toList();
    }

    @Transactional
    public void sendMission() {
        // 모든 살아있는(살아있는 유저가 한명이라도 들어가있는) 팀들을 조회
        List<String> allTeamIds = userTeamRepository.findAllByTeamNotNull()
                .stream()
                .map(UserTeam::getTeam)
                .map(Team::getId)
                .map(String::valueOf)
                .distinct()
                .toList();

        if(allTeamIds.isEmpty()) { // 팀이 없는 경우
            throw new CommonException(ErrorCode.NOT_FOUND_TEAM);
        }

        String missionContent = getNextMission(); // 랜덤 미션 생성

        // 모든 팀에 미션을 전송
        for(String teamId : allTeamIds) {
            List<UserTeam> userTeams = userTeamRepository.findAllByTeamId(Long.parseLong(teamId));

            ChatMessage missionMessage = ChatMessage.builder()
                    .type("missionType")
                    .content(missionContent)
                    .date(OffsetDateTime.now())
                    .build(); // 미션 메시지 생성

            for(UserTeam userTeam : userTeams) {
                missionRepository.save(
                        Mission.fromMissionChat(missionMessage.getContent(), userTeam)
                );
            }

            ChatMessageResponseDto responseDto = convertToResponseDto(missionMessage);
            messagingTemplate.convertAndSend("/subscribe/team/" + teamId, responseDto);
        }
        scheduleSendMissionTask();
    }

    @PostConstruct
    public void initialize() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        resetMissions();
        scheduleSendMissionTask();;
    }

    private void resetMissions() {
        availableMissions = new ArrayList<>(Constants.MISSION);
        usedMissions.clear();
    }

    private String getNextMission() {
        if (availableMissions.isEmpty()) {
            resetMissions();
        }
        int index = random.nextInt(availableMissions.size());
        String mission = availableMissions.remove(index);
        usedMissions.add(mission);
        return mission;
    }

    private void scheduleSendMissionTask() {
        int delay = 3000 + random.nextInt(1800); // 50분 기본 + 최대 30분 랜덤 (50분 ~ 80분)
        scheduler.schedule(this::sendMission, delay, TimeUnit.SECONDS);
    }

    private ChatMessageResponseDto convertToResponseDto(ChatMessage message) {
        return ChatMessageResponseDto.builder()
                .type(message.getType())
                .content(message.getContent())
                .sendTime(message.getDate().toString())
                .build();
    }
}
