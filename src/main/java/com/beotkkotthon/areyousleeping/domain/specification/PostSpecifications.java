package com.beotkkotthon.areyousleeping.domain.specification;

import com.beotkkotthon.areyousleeping.domain.Post;
import org.springframework.data.jpa.domain.Specification;

public class PostSpecifications {
    public static Specification<Post> hasKeyword(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.trim().isEmpty()) return null;
            return cb.like(cb.lower(root.get("title")), "%" + keyword.toLowerCase() + "%");
        };
    }
}
