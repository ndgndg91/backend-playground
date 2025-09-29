package com.ndgndg91.codilitytest.article;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ArticleSpecificationFactory {
    // 1. Find all article that contain a specific tag
    public Specification<Article> allArticlesWithTag(String tag) {
        return new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.isMember(tag, root.get("tags"));
            }
        };
    }

    // 2. Find an article by its title
    public Specification<Article> byTitle(String title) {
        return new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.equal(root.get("title"), title);
            }
        };
    }

    // 3. Find all articles by a title part - searching is not case-sensitive
    public Specification<Article> byTitlePart(String titlePart) {
        return new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.like(
                        builder.lower(root.get("title")),
                        "%" + titlePart.toLowerCase() + "%"
                );
            }
        };
    }
}