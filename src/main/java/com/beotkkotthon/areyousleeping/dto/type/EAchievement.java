package com.beotkkotthon.areyousleeping.dto.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EAchievement {
    JAMMANBO("잠만보", "첫 밤샘 참여", 1),
    OWL("올빼미", "밤샘 참여 누적 시간 10시간 이상", 2),
    DAYANDNIGHTCHANGER("낮밤이바뀐자", "연속 밤샘 참여4일 이상 보유", 3),
    GURDIANOFNIGHT("밤의수호자", "밤샘 총 시간 50시간 이상", 4),
    ALLNIGHTMASTER("밤샘마스터", "최소 6시간 이상 밤샘을 채운 횟수가 10회 이상", 5),
    LONELYALLNIGHTER("고독한밤샘가", "1인 밤샘 5번 이상", 2),
    GOVERNEROFNIGHT("밤을다스리는자", "방장으로 참여한 밤샘 총시간 50시간 이상", 4),
    SLEEPLESSMASTER("불면의달인", "밤샘 총시간 100시간", 6);

    private final String title;
    private final String content;
    private final Integer difficulty;
    @Override
    public String toString() {
        return title;
    }
}
