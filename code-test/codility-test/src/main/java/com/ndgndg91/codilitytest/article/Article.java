package com.ndgndg91.codilitytest.article;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Article {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Author author;

    @Column(unique = true)
    private String title;

    private String content;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    private List<String> tags = new ArrayList<>();

    public Article(Author author, String title) {
        this.author = author;
        this.title = title;
    }

    private Article() {}

    public void setContent(String content) {
        this.content = content;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public Long getId() {
        return id;
    }

    public Author getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<String> getTags() {
        return new ArrayList<>(tags);
    }
}
