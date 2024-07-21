package com.beotkkotthon.areyousleeping.repository;

import com.beotkkotthon.areyousleeping.domain.Like;
import com.beotkkotthon.areyousleeping.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {
    Optional<Like> findByPostIdAndUserId(Long postId, Long userId);
    List<Like> findAllByUserId(Long userId);
    Integer countByPost(Post post);
}
