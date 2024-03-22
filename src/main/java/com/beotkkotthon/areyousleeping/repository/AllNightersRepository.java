package com.beotkkotthon.areyousleeping.repository;

import com.beotkkotthon.areyousleeping.domain.AllNighters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AllNightersRepository extends JpaRepository<AllNighters, Long> {
    AllNighters findByUserTeamId(Long userId);
    @Query("SELECT an FROM AllNighters an WHERE an.userTeam.user.id = :userId")
    List<AllNighters> findByUserId(@Param("userId") Long userId);
    @Query("SELECT an FROM AllNighters an WHERE an.userTeam.user.id = :userId AND an.userTeam.team IS NULL AND an.endAt BETWEEN :start AND :end")
    List<AllNighters> findByUserIdAndEndAtBetween(@Param("userId") Long userId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
