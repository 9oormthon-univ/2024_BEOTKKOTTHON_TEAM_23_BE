package com.beotkkotthon.areyousleeping.repository;

import com.beotkkotthon.areyousleeping.domain.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {
}
