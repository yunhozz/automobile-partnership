package com.automobilepartnership.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseInfo {

    @Column(length = 10)
    private String name;

    @Column(columnDefinition = "tinyint")
    private int age;

    private String residence;

    public BaseInfo(String name, int age, String residence) {
        this.name = name;
        this.age = age;
        this.residence = residence;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateInfo(String name, int age, String residence) {
        updateName(name);
        this.age = age;
        this.residence = residence;
    }
}