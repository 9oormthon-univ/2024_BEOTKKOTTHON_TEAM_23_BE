package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.domain.AllNighters;
import com.beotkkotthon.areyousleeping.dto.response.AllNightersDto;
import com.beotkkotthon.areyousleeping.repository.AllNightersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AllNightersService {
    private final AllNightersRepository allNightersRepository;
    @Transactional(readOnly = true)
    public Map<String, Object> readAllNighters(Long userId, int year, int month) {

        // 시작 날짜와 종료 날짜 설정
        LocalDateTime startOfMonth = YearMonth.of(year, month).atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = YearMonth.of(year, month).atEndOfMonth().atTime(23, 59, 59);

        List<AllNighters> allNightersList = allNightersRepository.findByUserIdAndEndAtBetween(userId, startOfMonth, endOfMonth);

        List<AllNightersDto> allNightersDtos = allNightersList.stream()
                .map(allNighter -> AllNightersDto.builder()
                        .id(allNighter.getUserTeam().getId())
                        .startAt(allNighter.getStartAt())
                        .endAt(allNighter.getEndAt())
                        .duration(allNighter.getDuration())
                        .build())
                .toList();

        List<AllNighters> totalAllNightersList = allNightersRepository.findByUserId(userId);

        int totalDuration = totalAllNightersList.stream()
                .map(AllNighters::getDuration)
                .reduce(0, Integer::sum);
        int totalAllNighters = totalAllNightersList.size();

        Map<String, Object> result = new HashMap<>();
        result.put("totalDuration", totalDuration);
        result.put("totalAllNighters", totalAllNighters);
        result.put("allNightersRecords", allNightersDtos);
        return result;
    }
}
