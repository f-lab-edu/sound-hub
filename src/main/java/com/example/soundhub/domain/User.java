package com.example.soundhub.domain;


import lombok.Builder;
import lombok.Getter;


@Getter
public class User {
    private Long id;
    private final String name;
    private final String login_id;
    private final String password;


    @Builder
    public User(String name, String login_id, String password) {
        this.name = name;
        this.login_id = login_id;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!name.equals(user.name)) return false;
        if (!login_id.equals(user.login_id)) return false;
        return password.equals(user.password);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + login_id.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}
