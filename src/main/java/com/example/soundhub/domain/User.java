package com.example.soundhub.domain;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@EqualsAndHashCode
@Getter
public class User {
    private final String name;
    private final String login_id;
    private final String password;
    private Long id;


    @Builder
    public User(String name, String login_id, String password) {
        this.name = name;
        this.login_id = login_id;
        this.password = password;
    }
}