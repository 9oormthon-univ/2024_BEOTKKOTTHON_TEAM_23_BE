package com.beotkkotthon.areyousleeping.domain.specification;

import com.beotkkotthon.areyousleeping.domain.Team;
import org.springframework.data.jpa.domain.Specification;

public class TeamSpecifications {

    public static Specification<Team> hasKeyword(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null) return null;
            return cb.like(root.get("name"), "%" + keyword + "%");
        };
    }
    public static Specification<Team> hasGoal(String goal) {
        return (root, query, cb) -> {
            if (goal == null) return null;
            return cb.equal(root.get("goal"), goal);
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
            if (isEmpty == null) return null;
            return cb.equal(root.get("isEmpty"), isEmpty);
        };
    }
    public static Specification<Team> isPublic(Boolean isPublic) {
        return (root, query, cb) -> {
            if (isPublic == null) return null;
            return cb.equal(root.get("isPublic"), isPublic);
        };
    }
}
