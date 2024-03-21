package com.beotkkotthon.areyousleeping.repository;

import com.beotkkotthon.areyousleeping.domain.AllNighters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllNightersRepository extends JpaRepository<AllNighters, Long> {
    AllNighters findByUserTeamId(Long userId);
}
