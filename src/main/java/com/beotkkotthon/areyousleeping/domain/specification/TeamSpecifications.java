package com.beotkkotthon.areyousleeping.domain.specification;

import com.beotkkotthon.areyousleeping.domain.Team;
import org.springframework.data.jpa.domain.Specification;

public class TeamSpecifications {

    public static Specification<Team> hasKeyword(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.trim().isEmpty()) return null;
            return cb.like(cb.lower(root.get("title")), "%" + keyword.toLowerCase() + "%");
        };
    }
    public static Specification<Team> hasCategory(String category) {
        return (root, query, cb) -> {
            if (category == null) return null;
            return cb.equal(root.get("category"), category);
        };
    }
    public static Specification<Team> isEmpty(Boolean isEmpty) {
        return (root, query, cb) -> {
            if (isEmpty == null || !isEmpty) return null;
            return cb.lessThan(root.get("currentNum"), root.get("maxNum"));
        };
    }
    public static Specification<Team> isSecret(Boolean isSecret) {
        return (root, query, cb) -> {
            if (isSecret == null) return null;
            if (isSecret) {
                return cb.isTrue(root.get("isSecret"));
            } else {
                return cb.isFalse(root.get("isSecret"));
            }
        };
    }
}
