package com.beotkkotthon.areyousleeping.domain;

import com.amazonaws.services.s3.S3AccessPointResource;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post_image")
public class PostImage {
    @Id
    @Column(name="post_image_id")
    private Long id;

    @JoinColumn(name = "post_id", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @Column(name="image_url", nullable = false)
    private String imageUrl;

    @Builder
    public PostImage(Post post, String imageUrl){
        this.post = post;
        this.imageUrl = imageUrl;
    }
}
