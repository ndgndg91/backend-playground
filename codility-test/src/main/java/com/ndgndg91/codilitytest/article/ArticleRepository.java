package com.ndgndg91.codilitytest.article;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {

    @Query("SELECT a FROM Article a WHERE a.author.name = :#{#author.name} AND a.author.login = :#{#author.login}")
    List<Article> findAllWrittenBy(@Param("author") Author author);

    @Query("SELECT DISTINCT t FROM Article a JOIN a.tags t WHERE a.title = :title")
    List<String> findAllTagsOfArticle(@Param("title") String title);

    @Query("SELECT a FROM Article a WHERE (a.author.name = :#{#author.name} AND a.author.login = :#{#author.login}) OR a.title = :title")
    List<Article> findByAuthorOrTitle(@Param("author") Author author, @Param("title") String title);

    @Query("SELECT a.author FROM Article a WHERE a.title = :title")
    Optional<Author> findAuthorOfArticleByTitle(@Param("title") String title);
}
