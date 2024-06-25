package com.beotkkotthon.areyousleeping.repository;

import com.beotkkotthon.areyousleeping.domain.Team;
import com.beotkkotthon.areyousleeping.domain.User;
import com.beotkkotthon.areyousleeping.domain.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {

    List<UserTeam> findAllByUserId(Long userId);
    List<UserTeam> findAllByUserIdOrderByCreatedAtDesc(Long userId);
    List<UserTeam> findAllByTeamNotNull();
    UserTeam findByUserIdAndTeamId(Long userId, Long teamId);
    boolean existsByUserAndTeam(User user, Team team);

    List<UserTeam> findAllByTeamId(Long teamId);
    List<UserTeam> findAllByHistoryTeamId(Long historyTeamId);

    Long countByTeamIdAndIsActiveTrue(Long teamId);

    List<UserTeam> findByTeamIdOrderByIdAsc(Long teamId);

    Optional<UserTeam> findByUserId(Long userId);
}
