package com.beotkkotthon.areyousleeping.repository;

import com.beotkkotthon.areyousleeping.domain.AchievementRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRateRepository extends JpaRepository<AchievementRate, Long> {
}
