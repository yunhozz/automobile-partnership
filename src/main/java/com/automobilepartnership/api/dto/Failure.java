package com.automobilepartnership.api.dto;

import lombok.Getter;

@Getter
public class Failure<T> implements Result {

    private final T data;

    public Failure(T data) {
        this.data = data;
    }
}