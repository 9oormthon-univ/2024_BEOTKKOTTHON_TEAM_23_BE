package com.beotkkotthon.areyousleeping.repository;

import com.beotkkotthon.areyousleeping.domain.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
    Optional<Mission> findFirstByUserTeamIdOrderByIssuedAtDesc(Long userTeamId); // 내림차순 정렬(가장 늦은 날짜가 가장 먼저)
    List<Mission> findAllByUserTeamIdOrderByIssuedAtAsc(Long userTeamId); // 오름차순 정렬(가장 빠른 날짜가 가장 먼저)
}
