package com.ndgndg91.codilitytest.article;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleTestController {

    private final ArticleRepository repository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/api/test")
    public void test() {

        Article entity = new Article(new Author("기리", ""), "테스트2");
        entity.addTag("사랑");
        entity.addTag("열정");
        repository.save(entity);
    }

    @GetMapping("/api/find/test")
    public void test2() {
        var articles = repository.findAllWrittenBy(new Author("기리", ""));
        logger.info("{}", articles);
        var tags = repository.findAllTagsOfArticle("테스트2");
        logger.info("{}", tags);

        var article = repository.findByAuthorOrTitle(new Author("기리", ""), "테스트");
        logger.info("{}", article);

        var author = repository.findAuthorOfArticleByTitle("테스트");
        var author2 = repository.findAuthorOfArticleByTitle("테스트2");
        logger.info("{}, {}", author, author2);
    }
}
