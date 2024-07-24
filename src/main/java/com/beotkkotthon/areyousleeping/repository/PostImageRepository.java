package com.beotkkotthon.areyousleeping.repository;

import com.beotkkotthon.areyousleeping.domain.PostImage;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long>{
    @Query("SELECT pi.imageUrl FROM PostImage pi WHERE pi.post.id = :postId")
    List<String> findAllImageUrlByPostId(Long postId);
    List<PostImage> findAllByPostId(Long postId);
    Integer countByPostId(Long postId);
}
