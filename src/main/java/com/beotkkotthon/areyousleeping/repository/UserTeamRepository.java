package com.beotkkotthon.areyousleeping.repository;

import com.beotkkotthon.areyousleeping.domain.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {
    UserTeam findByUserIdAndTeamId(Long userId, Long teamId);
    List<UserTeam> findAllByTeamId(Long teamId);
}
