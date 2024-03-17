package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.domain.Mission;
import com.beotkkotthon.areyousleeping.domain.UserTeam;
import com.beotkkotthon.areyousleeping.domain.nosql.ChatMessage;
import com.beotkkotthon.areyousleeping.domain.nosql.ChatMessageList;
import com.beotkkotthon.areyousleeping.dto.request.MissionImageDto;
import com.beotkkotthon.areyousleeping.dto.request.MissionTextDto;
import com.beotkkotthon.areyousleeping.dto.response.ChatMessageResponseDto;
import com.beotkkotthon.areyousleeping.dto.response.MissionDto;
import com.beotkkotthon.areyousleeping.repository.ChatMessageListRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final SimpMessagingTemplate messagingTemplate;
    private final MissionRepository missionRepository;
    private final UserTeamRepository userTeamRepository;
    private final ChatMessageListRepository chatMessageListRepository;
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

    public List<MissionDto> getMissionTimeline(Long userId, Long teamId) {
        UserTeam userTeam = userTeamRepository.findByUserIdAndTeamId(userId, teamId);
        return missionRepository.findAllByUserTeamIdOrderByIssuedAtAsc(userTeam.getId())
                .stream()
                .map(MissionDto::fromEntity)
                .collect(Collectors.toList());
    }
    @Transactional
    public void sendMission() {
        List<String> allTeamIds = chatMessageListRepository.findAll()
                .stream()
                .map(ChatMessageList::getId)
                .toList();

        String missionContent = getNextMission();

        for(String teamId : allTeamIds) {
            ChatMessage missionMessage = ChatMessage.builder()
                    .type("missionType")
                    .content(missionContent)
                    .date(OffsetDateTime.now())
                    .build();

            List<UserTeam> userTeams = userTeamRepository.findAllByTeamId(Long.parseLong(teamId));
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
        availableMissions = new ArrayList<>(List.of(
                "질문: 당신은 투명인간이 되었다. 가장 먼저 하고싶은 것은?",
                "질문: 지금 당장 시간여행을 어디로 갈 것인가?",
                "질문: 밥 맛 똥먹기 vs 똥 맛 밥먹기",
                "질문: 외계인이 지구에 온다면, 가장 먼저 어디를 보여주고 싶나요?",
                "질문: 초능력을 하나만 가질 수 있다면, 무엇을 선택하겠습니까?",
                "질문: 좀비 아포칼립스가 발생했을 때, 가장 먼저 할 일은?",

                "사진: 세수하고 물기 닦지 않고 셀카 찍기",
                "사진: 지금 당장 생각나는거 하나 그려서 사진찍기",
                "사진: 지금 바로 눈 앞에 있는 것 찍기",
                "사진: 현재 당신의 발 모양을 찍어 보내기",
                "사진: 당신이 가진 가장 이상한 물건 찍어 보내기",
                "사진: 야간 모드로 창밖 풍경 사진 찍기",
                "사진: 얼굴로 웃기기"
        ));
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
