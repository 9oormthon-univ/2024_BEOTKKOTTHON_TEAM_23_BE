package com.beotkkotthon.areyousleeping.repository;

import com.beotkkotthon.areyousleeping.domain.Following;
import com.beotkkotthon.areyousleeping.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowingRepository extends JpaRepository<Following, Long> {
    Optional<Following> findBySenderAndReceiver(User sender, User receiver);

    @Query("SELECT new FollowerInfoDto(u.id, u.nickname, u.title) " +
            "FROM Following f JOIN f.sender u " +
            "WHERE f.receiver.id = :receiverId")
    List<FollowerInfoDto> findMyFollowersWithNicknameAndTitle(@Param("receiverId") Long receiverId);
}
