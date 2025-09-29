package com.ndgndg91.codilitytest.article;


import jakarta.persistence.Embeddable;

@Embeddable
public class Author {
    private String name;
    private String login;

    public Author(String name, String login) {
        this.name = name;
        this.login = login;
    }

    private Author() {}

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }
}