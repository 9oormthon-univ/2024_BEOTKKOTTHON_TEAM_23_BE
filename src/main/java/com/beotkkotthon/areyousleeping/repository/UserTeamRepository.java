package com.beotkkotthon.areyousleeping.repository;

import com.beotkkotthon.areyousleeping.domain.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {
}
