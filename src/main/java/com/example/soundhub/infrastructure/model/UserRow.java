package com.example.soundhub.infrastructure.model;

import com.example.soundhub.domain.model.User;
import lombok.Getter;

@Getter
public class UserRow {
    private final Long id;
    private final String name;
    private final int age;
    private final String address;

    public UserRow(Long id, String name, int age, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public User toDomain() {
        return new User(id, name, age, address);
    }
}
